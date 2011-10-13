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

class JavassistScalaTemplateBuilder(_registry : TemplateRegistry)
  extends JavassistTemplateBuilder(_registry) with ScalaObjectMatcher with ScalaPropertyFinder{

  val classPool = this.pool


  // matching and find properties functions is implemented in trait

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



  // builder context
  override def createBuildContext() = {
    new ScalaBuildContext(this)
  }
}

abstract class JavassistScalaTemplate[T](var targetClass : Class[T], var templates : Array[Template[_]]) extends AbstractTemplate[T]{


}