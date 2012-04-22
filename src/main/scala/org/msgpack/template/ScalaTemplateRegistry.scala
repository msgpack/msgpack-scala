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
package org.msgpack.template

import builder._
import collection.mutable._
import java.math.BigInteger
import java.nio.ByteBuffer
import org.msgpack.`type`.Value
import org.msgpack.template.TemplateRegistry._
import java.lang.reflect.Type
import org.msgpack.scalautil.ScalaSigUtil
import org.msgpack.MessageTypeException

/**
 * 
 * User: takeshita
 * Create: 11/10/12 21:21
 */

class ScalaTemplateRegistry extends TemplateRegistry(null){



  {
    //def anyTemplate[T] = AnyTemplate.getInstance(this).asInstanceOf[Template[T]]

    val at = new AnyTemplate[Any](this)
    def anyTemplate[T] = at.asInstanceOf[Template[T]]
    register(new ImmutableListTemplate[Any](anyTemplate))
    register(new ImmutableMapTemplate(anyTemplate,anyTemplate))
    register(new DoubleLinkedListTemplate(anyTemplate))
    register(new LinkedListTemplate(anyTemplate))
    register(new ListBufferTemplate(anyTemplate))
    register(new MutableListCTemplate(anyTemplate))
    register(new MutableHashMapTemplate(anyTemplate,anyTemplate))
    register(new MutableListMapTemplate(anyTemplate,anyTemplate))
    register(new MutableLinkedHashMapTemplate(anyTemplate,anyTemplate))
    register(classOf[scala.collection.mutable.Map[_,_]],
    new MutableHashMapTemplate(anyTemplate,anyTemplate))
    register(classOf[scala.collection.mutable.Seq[_]],
      new LinkedListTemplate(anyTemplate))
    register(classOf[Seq[_]],new ImmutableListTemplate(anyTemplate))
    register(classOf[scala.collection.immutable.List[_]],new ImmutableListTemplate[Any](anyTemplate))
    register(classOf[java.util.Calendar],new CalendarTemplate)
    //tuples
    register(classOf[Tuple1[_]], new Tuple1Template[Any](anyTemplate))
    register(classOf[Tuple2[_,_]], new Tuple2Template[Any,Any](anyTemplate,anyTemplate))
    register(classOf[Tuple3[_,_,_]], new Tuple3Template[Any,Any,Any](anyTemplate,anyTemplate,anyTemplate))
    register(classOf[Tuple4[_,_,_,_]], new Tuple4Template[Any,Any,Any,Any](anyTemplate,anyTemplate,anyTemplate,anyTemplate))
    register(classOf[Tuple5[_,_,_,_,_]], new Tuple5Template[Any,Any,Any,Any,Any](anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate))
    register(classOf[Tuple6[_,_,_,_,_,_]], new Tuple6Template[Any,Any,Any,Any,Any,Any](anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate))
    register(classOf[Tuple7[_,_,_,_,_,_,_]], new Tuple7Template[Any,Any,Any,Any,Any,Any,Any](anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate))
    register(classOf[Tuple8[_,_,_,_,_,_,_,_]], new Tuple8Template[Any,Any,Any,Any,Any,Any,Any,Any](anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate))
    register(classOf[Tuple9[_,_,_,_,_,_,_,_,_]], new Tuple9Template[Any,Any,Any,Any,Any,Any,Any,Any,Any](anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate))
    register(classOf[Tuple10[_,_,_,_,_,_,_,_,_,_]], new Tuple10Template[Any,Any,Any,Any,Any,Any,Any,Any,Any,Any](anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate))
    register(classOf[Tuple11[_,_,_,_,_,_,_,_,_,_,_]], new Tuple11Template[Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any](anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate))
    register(classOf[Tuple12[_,_,_,_,_,_,_,_,_,_,_,_]], new Tuple12Template[Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any](anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate))
    register(classOf[Tuple13[_,_,_,_,_,_,_,_,_,_,_,_,_]], new Tuple13Template[Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any](anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate))
    register(classOf[Tuple14[_,_,_,_,_,_,_,_,_,_,_,_,_,_]], new Tuple14Template[Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any](anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate))
    register(classOf[Tuple15[_,_,_,_,_,_,_,_,_,_,_,_,_,_,_]], new Tuple15Template[Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any](anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate))
    register(classOf[Tuple16[_,_,_,_,_,_,_,_,_,_,_,_,_,_,_,_]], new Tuple16Template[Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any](anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate))
    register(classOf[Tuple17[_,_,_,_,_,_,_,_,_,_,_,_,_,_,_,_,_]], new Tuple17Template[Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any](anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate))
    register(classOf[Tuple18[_,_,_,_,_,_,_,_,_,_,_,_,_,_,_,_,_,_]], new Tuple18Template[Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any](anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate))
    register(classOf[Tuple19[_,_,_,_,_,_,_,_,_,_,_,_,_,_,_,_,_,_,_]], new Tuple19Template[Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any](anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate))
    register(classOf[Tuple20[_,_,_,_,_,_,_,_,_,_,_,_,_,_,_,_,_,_,_,_]], new Tuple20Template[Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any](anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate))
    register(classOf[Tuple21[_,_,_,_,_,_,_,_,_,_,_,_,_,_,_,_,_,_,_,_,_]], new Tuple21Template[Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any](anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate))
    register(classOf[Tuple22[_,_,_,_,_,_,_,_,_,_,_,_,_,_,_,_,_,_,_,_,_,_]], new Tuple22Template[Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any](anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate,anyTemplate))
    // generics
    registerGeneric(classOf[scala.collection.immutable.List[_]],new GenericImmutableListTemplate())
    registerGeneric(classOf[scala.collection.immutable.Map[_,_]],new GenericImmutableMapTemplate())
    registerGeneric(classOf[scala.collection.immutable.Seq[_]],new GenericImmutableListTemplate())
    registerGeneric(classOf[scala.collection.Seq[_]],new GenericImmutableListTemplate())
    registerGeneric(classOf[Seq[_]],new GenericImmutableListTemplate())

    registerGeneric(classOf[DoubleLinkedList[_]],
      new GenericMutableListTemplate[DoubleLinkedListTemplate[_]]())
    registerGeneric(classOf[ListBuffer[_]],
      new GenericMutableListTemplate[ListBufferTemplate[_]]())
    registerGeneric(classOf[MutableList[_]],
      new GenericMutableListTemplate[MutableListCTemplate[_]]())
    registerGeneric(classOf[LinkedList[_]],
      new GenericMutableListTemplate[LinkedListTemplate[_]]())
    registerGeneric(classOf[scala.collection.mutable.Seq[_]],
      new GenericMutableListTemplate[LinkedListTemplate[_]]())

    registerGeneric(classOf[LinkedHashMap[_,_]],
      new GenericMutableMapTemplate[MutableLinkedHashMapTemplate[_,_]])
    registerGeneric(classOf[HashMap[_,_]],
      new GenericMutableMapTemplate[MutableHashMapTemplate[_,_]])
    registerGeneric(classOf[ListMap[_,_]],
      new GenericMutableMapTemplate[MutableListMapTemplate[_,_]])
    registerGeneric(classOf[scala.collection.mutable.Map[_,_]],
      new GenericMutableMapTemplate[MutableHashMapTemplate[_,_]])
    registerGeneric(classOf[Option[_]],new GenericOptionTemplate)
    registerGeneric(classOf[Either[_,_]],new GenericEitherTemplate)


    //tuples
    registerGeneric(classOf[Tuple1[_]], new GenericTuple1Template())
    registerGeneric(classOf[Tuple2[_,_]], new GenericTuple2Template())
    registerGeneric(classOf[Tuple3[_,_,_]], new GenericTuple3Template())
    registerGeneric(classOf[Tuple4[_,_,_,_]], new GenericTuple4Template())
    registerGeneric(classOf[Tuple5[_,_,_,_,_]], new GenericTuple5Template())
    registerGeneric(classOf[Tuple6[_,_,_,_,_,_]], new GenericTuple6Template())
    registerGeneric(classOf[Tuple7[_,_,_,_,_,_,_]], new GenericTuple7Template())
    registerGeneric(classOf[Tuple8[_,_,_,_,_,_,_,_]], new GenericTuple8Template())
    registerGeneric(classOf[Tuple9[_,_,_,_,_,_,_,_,_]], new GenericTuple9Template())
    registerGeneric(classOf[Tuple10[_,_,_,_,_,_,_,_,_,_]], new GenericTuple10Template())
    registerGeneric(classOf[Tuple11[_,_,_,_,_,_,_,_,_,_,_]], new GenericTuple11Template())
    registerGeneric(classOf[Tuple12[_,_,_,_,_,_,_,_,_,_,_,_]], new GenericTuple12Template())
    registerGeneric(classOf[Tuple13[_,_,_,_,_,_,_,_,_,_,_,_,_]], new GenericTuple13Template())
    registerGeneric(classOf[Tuple14[_,_,_,_,_,_,_,_,_,_,_,_,_,_]], new GenericTuple14Template())
    registerGeneric(classOf[Tuple15[_,_,_,_,_,_,_,_,_,_,_,_,_,_,_]], new GenericTuple15Template())
    registerGeneric(classOf[Tuple16[_,_,_,_,_,_,_,_,_,_,_,_,_,_,_,_]], new GenericTuple16Template())
    registerGeneric(classOf[Tuple17[_,_,_,_,_,_,_,_,_,_,_,_,_,_,_,_,_]], new GenericTuple17Template())
    registerGeneric(classOf[Tuple18[_,_,_,_,_,_,_,_,_,_,_,_,_,_,_,_,_,_]], new GenericTuple18Template())
    registerGeneric(classOf[Tuple19[_,_,_,_,_,_,_,_,_,_,_,_,_,_,_,_,_,_,_]], new GenericTuple19Template())
    registerGeneric(classOf[Tuple20[_,_,_,_,_,_,_,_,_,_,_,_,_,_,_,_,_,_,_,_]], new GenericTuple20Template())
    registerGeneric(classOf[Tuple21[_,_,_,_,_,_,_,_,_,_,_,_,_,_,_,_,_,_,_,_,_]], new GenericTuple21Template())
    registerGeneric(classOf[Tuple22[_,_,_,_,_,_,_,_,_,_,_,_,_,_,_,_,_,_,_,_,_,_]], new GenericTuple22Template())

    //register(classOf[AnyRef],new DynamicTemplate(this))
  }

  def register[T]( template : Template[T])(implicit manifest : Manifest[T]) : Unit = {
    this.register(manifest.erasure,template)
  }


  override def createTemplateBuilderChain() = {
    new ScalaTemplateBuilderChain(this)
  }

}

class ScalaTemplateBuilderChain(registry : TemplateRegistry,forceReflectionMode : Boolean) extends TemplateBuilderChain(registry){

  def this(_registry : TemplateRegistry) = this(_registry,false)

  private def enableDynamicCodeGeneration: Boolean = {
    try {
      return !forceReflectionMode && !System.getProperty("java.vm.name").equals("Dalvik")
    }
    catch {
      case e: Exception => {
        return true
      }
    }
  }

  override def reset(registry: TemplateRegistry, cl: ClassLoader) = {
    if (registry == null) {
      throw new NullPointerException("registry is null")
    }


    templateBuilders.add(new ArrayTemplateBuilder(registry))
    templateBuilders.add(new OrdinalEnumTemplateBuilder(registry))
    templateBuilders.add(new ScalaEnumTemplateBuilder(registry))
    if (enableDynamicCodeGeneration) {
      forceBuilder = new JavassistScalaTemplateBuilder(registry)
      if (cl != null) {
        forceBuilder.asInstanceOf[JavassistBeansTemplateBuilder].addClassLoader(cl)
      }

      val b = forceBuilder
      templateBuilders.add(b)
      val builder = new JavassistTemplateBuilder(registry)
      if (cl != null) {
        builder.addClassLoader(cl)
      }
      forceBuilder = builder
      templateBuilders.add(builder)
      templateBuilders.add(new JavassistBeansTemplateBuilder(registry))
    }
    else {
      forceBuilder = new ReflectionScalaTemplateBuilder(registry)

      templateBuilders.add(forceBuilder)
      val builder = new ReflectionTemplateBuilder(registry)
      templateBuilders.add(builder)
      templateBuilders.add(new OrdinalEnumTemplateBuilder(registry))
      templateBuilders.add(new ReflectionBeansTemplateBuilder(registry))
    }

  }
}