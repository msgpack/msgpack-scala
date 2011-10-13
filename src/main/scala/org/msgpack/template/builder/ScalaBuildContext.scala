//
// MessagePack for Scala
//
// Copyright (C) 2009-2011 FURUHASHI Sadayuki
//
//    Licensed under the Apache License, Version 2.0 (the "License");
//    you may not use this file except in compliance with the License.
//    You may obtain a copy of the License at
//
//        http://www.apache.org/licenses/LICENSE-2.0
//
//    Unless required by applicable law or agreed to in writing, software
//    distributed under the License is distributed on an "AS IS" BASIS,
//    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//    See the License for the specific language governing permissions and
//    limitations under the License.
//
package org.msgpack.template.builder

import java.lang.{String, Class}
import org.slf4j.{LoggerFactory, Logger}
import javassist.{ClassPool, CtNewConstructor, CtClass}
import org.msgpack.MessageTypeException
import java.lang.reflect.{Constructor, Modifier}
import org.msgpack.template.{TemplateRegistry, Template}

/**
 * 
 * User: takeshita
 * Create: 11/10/12 17:53
 */

class ScalaBuildContext(builder : JavassistScalaTemplateBuilder) extends BuildContext[ScalaFieldEntry](builder){

  private var logger : Logger = LoggerFactory.getLogger(classOf[ScalaBuildContext])


  val pool = builder.pool
  var originalClass : Class[_] = null
  var templates : Array[Template[_]]  = Array()
  var entries : Array[ScalaFieldEntry] = Array()

  def originalClassName = originalClass.getName()

  def buildTemplate(targetClass: Class[_],
                    entries: Array[ScalaFieldEntry],
                    templates: Array[Template[_]]) = {
    this.originalClass = targetClass
    this.templates = templates
    this.entries = entries
    build(originalClassName)
  }
  def writeTemplate(targetClass: Class[_],
                    entries: Array[ScalaFieldEntry],
                    templates: Array[Template[_]], directoryName: String) = {}

  def loadTemplate(targetClass: Class[_]) = {
    this.originalClass = targetClass
    load(originalClassName)
  }



  // build




  def setSuperClass() = {
    tmplCtClass.setSuperclass(pool.getCtClass(
      classOf[JavassistScalaTemplate[_]].getName()
    ))
  }



  def buildConstructor() = {
    val newCtCons = CtNewConstructor.make(
      Array[CtClass](pool.getCtClass(classOf[Class[_]].getName),
            pool.getCtClass(classOf[Template[_]].getName + "[]")),
      new Array[CtClass](0), tmplCtClass)
    tmplCtClass.addConstructor(newCtCons)
  }

  def buildInstance(c: Class[_]) = {
    var cons: Constructor[_] = c.getConstructor(
      classOf[Class[Any]], classOf[Array[Template[Any]]])
    cons.newInstance(originalClass,templates).asInstanceOf[Template[_]]
  }

  //for write

  def buildWriteMethodBody() = {
    val builder = new StringBuilder()

    // null check
    builder.append("""
{
  if($2 == null) {
    if($3) {
      throw new %s("Attempted to write null");
    }
    $1.writeNil();
    return;
  }
""".format(classOf[MessageTypeException].getName()))

    // constructor
    builder.append("""  %s _$$_t = (%s) $2;
  $1.writeArrayBegin(%d);
""".format(originalClassName,originalClassName,entries.length))

    // fields
    var index = 0
    for( e <- entries){
      if(!e.available_?){
        builder.append("  $1.writeNil();\n")
      }else if(e.primitive_?){
        writePrimitiveValue(builder,index,e)
      }else if(e.nullable_?){
        writeNullableMethod(builder,index,e)
      }else{
        writeNotNullableMethod(builder,index,e)
      }
      index += 1
    }

    // cap
    builder.append("""  $1.writeArrayEnd();
}""")

    builder.toString()
  }
  protected def writePrimitiveValue(builder : StringBuilder,index : Int, entry : ScalaFieldEntry) = {
    builder.append("""  $1.%s(_$$_t.%s());
""".format(primitiveWriteName(entry.getType),entry.getName))
  }

  protected def writeNullableMethod(builder : StringBuilder,index : Int, entry : ScalaFieldEntry) = {
    builder.append("""  if(_$$_t.%s() == null){
    $1.writeNil();
  }else{
    templates()[%d].write($1, _$$_t.%s());
  }
""".format(entry.getName,index,entry.getName))
  }

  protected def writeNotNullableMethod(builder : StringBuilder,index : Int, entry : ScalaFieldEntry) = {
    builder.append("""  if(_$$_t.%s() == null){
    throw new %s();
  }else{
    templates()[%d].write($1, _$$_t.%s());
  }
""".format(entry.getName,classOf[MessageTypeException].getName(),index,entry.getName))
  }

  // for read

  def buildReadMethodBody() = {
    val builder = new StringBuilder
    // init
    builder.append("""
{
  if( !$3 && $1.trySkipNil()) {
    return null;
  }
  %s _$$_t;
  if ($2 == null){
    _$$_t = %s;
  } else {
    _$$_t = (%s) $2;
  }
  $1.readArrayBegin();
""".format(originalClassName,selectGoodConstructor,originalClassName))

    // fields
    var index = 0
    for( e <- entries){
      if(!e.available_?){
        builder.append("  $1.skip();\n")
      }else if(e.optional_?){
        readOptionalMethod(builder,index,e)
      }else if(e.primitive_?){
        readPrimitive(builder,index,e)
      }else{
        readAnyRef(builder,index,e)
      }

      index += 1
    }

    //cap
    builder.append("""
  $1.readArrayEnd();
  return _$$_t;
}""")

    builder.toString()
  }

  /**
   * check constructor and companion class.
   */
  protected def selectGoodConstructor() : String = {
    try{
      val cons = originalClass.getConstructor()
      if(Modifier.isPublic(cons.getModifiers)){
        return "new %s()".format(originalClassName)
      }
    }catch{
      case e : NoSuchMethodException =>
    }
    try{
      val c = originalClass.getClassLoader.loadClass(originalClass.getName + "$")
      if(Modifier.isPublic(c.getModifiers())){
        val m = c.getMethod("apply")
        if(Modifier.isPublic(m.getModifiers) &&
          originalClass.isAssignableFrom(m.getReturnType)){

          val staticField = c.getDeclaredField("MODULE$")
          return "%s.%s.apply()".format(c.getName,staticField.getName)
        }
      }

    }catch{
      case e : ClassNotFoundException =>
      case e : NoSuchMethodException =>
      case e : NoSuchFieldException =>
    }
    throw new MessageTypeException("Can't find plain constructor or companion object")

  }

  def readOptionalMethod(builder : StringBuilder , index : Int , entry : ScalaFieldEntry) = {
    builder.append("""
  if ( $1.trySkipNil()) {
    _$$_t.%s_$eq(null);
  }else{
  """.format(entry.getName()))
    readAnyRef(builder,index,entry)
    builder.append("\n  }")
  }

  def readPrimitive(builder : StringBuilder , index : Int , entry : ScalaFieldEntry) = {
    builder.append("""
  _$$_t.%s_$eq( $1.%s());""".format(entry.getName,primitiveReadName(entry.getType)))
  }

  def readAnyRef(builder : StringBuilder , index : Int , entry : ScalaFieldEntry) = {
    builder.append("""
  _$$_t.%s_$eq( (%s) (this.templates()[%d].read($1,_$$_t.%s()) ) );""".format(
      entry.getName,entry.getJavaTypeName,index,entry.getName()))
  }




}