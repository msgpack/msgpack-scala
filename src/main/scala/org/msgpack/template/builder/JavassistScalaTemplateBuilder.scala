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

import org.msgpack.unpacker.Unpacker
import org.msgpack.packer.Packer
import java.lang.{String, Class}
import org.msgpack.template._
import collection.immutable.ListMap
import java.lang.annotation.{ Annotation => JAnnotation}
import java.lang.reflect.{Field, Modifier, Method, Type}
import org.msgpack.annotation._

/**
 * 
 * User: takeshita
 * Create: 11/10/12 16:39
 */

class JavassistScalaTemplateBuilder(_registry : TemplateRegistry) extends JavassistTemplateBuilder(_registry){

  val classPool = this.pool


  // matching

  override def matchType(targetType: Type, hasAnnotation: Boolean) = {

    val c : Class[_] = targetType.asInstanceOf[Class[_]]
    (isAnnotated(c, classOf[MessagePackMessage]) || isAnnotated(c, classOf[Message]) ) &&
    classOf[ScalaObject].isAssignableFrom(c)
  }

  private def isAnnotated(targetType : Class[_], annotation : Class[_ <: JAnnotation]) = {
    targetType.getAnnotation(annotation) != null

  }

  // build template



  override def buildTemplate[T](targetClass: Class[T], entries: Array[FieldEntry]) = {
    val templates : Array[Template[_]] = toTemplates(entries)
    val bc = new ScalaBuildContext(this)

    //cast manually
    val sEntries = new Array[ScalaFieldEntry](entries.length)
    for(i <- 0 until entries.length){
      sEntries(i) = entries(i).asInstanceOf[ScalaFieldEntry]
    }
    bc.buildTemplate(targetClass,sEntries,templates).asInstanceOf[Template[T]]
  }

  private def toTemplates[T]( entries : Array[FieldEntry]) = {
    val templates : Array[Template[_]] = new Array(entries.length)
    var i = 0
    for( e <- entries){
      if(e.isAvailable){
        val template = registry.lookup(e.getGenericType)
        templates(i) = template
      }else{
        templates(i) = null
      }
      i += 1
    }
    templates
  }


  type Property = (Method,Method,Field)
  type PropertySet = (String,Property)

  override def toFieldEntries(targetClass: Class[_], from: FieldOption) = {
    val props = findPropertyMethods(targetClass) filter( !hasAnnotation(_,classOf[Ignore]))
    val indexed = indexing(props)
    indexed.map(convertToScalaFieldEntry(_))
  }

  def setter_?(method : Method) : Boolean = {
    val name = method.getName
    Modifier.isPublic(method.getModifiers) &&
    method.getReturnType.getName == "void" &&
    !name.startsWith("_") &&
    name.endsWith("_$eq") &&
    method.getParameterTypes.length == 1
  }

  def getter_?(method : Method) : Boolean = {
    !method.getName.startsWith("_") &&
    Modifier.isPublic(method.getModifiers) &&
    method.getReturnType.getName != "void" &&
    method.getParameterTypes.length == 0

  }




  def findPropertyMethods(targetClass: Class[_]) : Map[String,Property] = {
    var getters : Map[String,Method] = ListMap.empty
    var setters : Map[String,Method] = ListMap.empty

    def extractName( n : String) = {
      n.substring(0,n.length - 4)
    }

    //Find getters and setters
    for( m <- targetClass.getMethods){
      if(setter_?(m)){
        val valType = m.getParameterTypes()(0).getName
        setters +=( (extractName(m.getName) + ":" + valType) -> m)
      }else if(getter_?(m)){
        val valType = m.getReturnType.getName
        getters +=( (m.getName + ":" + valType) -> m)
      }
    }

    var props : Map[String,Property] = ListMap.empty

    def sameType_?( getter : Method,setter : Method) = {
      getter.getReturnType == setter.getParameterTypes()(0)
    }
    // order of methods changes depends on call order, NOT declaration.

    def getterAndSetter(name : String) : Option[(Method,Method)] = {
      if(getters.contains(name) && setters.contains(name)){
        val getter = getters(name)
        val setter = setters(name)
        if(getter.getReturnType == setter.getParameterTypes()(0)){
          Some(getter -> setter)
        }else{
          None
        }
      }else None
    }
    def recursiveFind( clazz : Class[_]) : Unit = {
      if(clazz.getSuperclass != classOf[Object]){
        recursiveFind(clazz.getSuperclass)
      }
      for(f <- clazz.getDeclaredFields){
        val name =f.getName
        getterAndSetter(name + ":" + f.getType().getName()) match{
          case Some((g,s)) => props +=( name -> (g,s,f))
          case None => {
            // if filed starts with "_" exists, it's also valid field for message pack.
            // This rule is to support custom getter and setter.
            if(name.startsWith("_")){
              val sname = name.substring(1)
              getterAndSetter(sname + ":" + f.getType().getName()) match{
                case Some((g,s)) => props +=( sname -> (g,s,f))
                case None =>
              }
            }
          }
        }
      }
    }
    recursiveFind(targetClass)

    props
  }

  def indexing( props : Map[String , Property]) : Array[PropertySet] = {
    val indexed = new Array[PropertySet](props.size)

    var notIndexed : List[PropertySet]  = Nil

    for(s <- props){
      val i = getAnnotation(s,classOf[Index])
      if(i == null){
        notIndexed = notIndexed :+ s
      }else{
        val index = i.value
        if(indexed(index) != null){
          throw new TemplateBuildException("duplicated index: "+index);
        }else{
          try{
            indexed(index) = s
          }catch{
            case e : Exception => {
              throw new TemplateBuildException("invalid index: %s index must be 0 <= x < %s".format(index,indexed.length));
            }
          }
        }
      }
    }
    for( i <- 0 until indexed.length ){
      if(indexed(i) == null){
        indexed(i) = notIndexed.head
        notIndexed = notIndexed.drop(1)
      }
    }


    indexed
  }

  def convertToScalaFieldEntry( propInfo : PropertySet) = {
    val entry = new ScalaFieldEntry(propInfo._1,
      readFieldOption(propInfo,FieldOption.OPTIONAL),
      readValueType(propInfo),
      readGenericType(propInfo),
      propInfo._2._1,
      propInfo._2._2
    )

    entry
  }
  def readFieldOption(prop : PropertySet , implicitOption : FieldOption) = {
    if(hasAnnotation(prop,classOf[Optional])){
      FieldOption.OPTIONAL
    } else if(hasAnnotation(prop,classOf[NotNullable])){
      FieldOption.NOTNULLABLE
    }else if(hasAnnotation(prop,classOf[Ignore])){
      FieldOption.IGNORE
    }else{
      if(readValueType(prop).isPrimitive){
        FieldOption.NOTNULLABLE
      }else{
        FieldOption.OPTIONAL
      }
    }

  }
  def readValueType(prop : PropertySet) = {
    prop._2._1.getReturnType
  }
  def readGenericType(prop : PropertySet) = {
    prop._2._1.getGenericReturnType
  }


  def hasAnnotation[T <: JAnnotation](prop : PropertySet , classOfAnno : Class[T]) : Boolean = {
    val getter = prop._2._1
    val setter = prop._2._2
    val field = prop._2._3
    getter.getAnnotation(classOfAnno) != null ||
    setter.getAnnotation(classOfAnno) != null ||
      {if(field != null) field.getAnnotation(classOfAnno) != null
    else false}
  }
  def getAnnotation[T <: JAnnotation](prop : PropertySet , classOfAnno : Class[T]) : T = {
    val getter = prop._2._1
    val setter = prop._2._2
    val field = prop._2._3



    val a = getter.getAnnotation(classOfAnno)
    if(a != null){
      a
    }else{
      val b = setter.getAnnotation(classOfAnno)
      if(b != null){
        b
      }else if(field != null){
        field.getAnnotation(classOfAnno)
      }else{
        null.asInstanceOf[T]
      }
    }
  }

  // builder context
  override def createBuildContext() = {
    new ScalaBuildContext(this)
  }
}

abstract class JavassistScalaTemplate[T](var targetClass : Class[T], var templates : Array[Template[_]]) extends AbstractTemplate[T]{


}