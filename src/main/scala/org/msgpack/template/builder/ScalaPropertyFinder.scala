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

import org.msgpack.template.FieldOption
import collection.immutable.ListMap
import org.msgpack.annotation.{NotNullable, Optional, Index, Ignore}
import java.lang.annotation.{ Annotation => JAnnotation}
import tools.scalap.scalax.rules.scalasig._
import java.lang.reflect.{Modifier, Field, Method, Type => JType, ParameterizedType}
import org.msgpack.scalautil.ScalaSigUtil

/**
 * Combination with java reflection and scalap.ScalaSigParser
 * User: takeshita
 * Create: 11/10/13 11:53
 */

trait ScalaPropertyFinder{

  self : AbstractTemplateBuilder =>

  type Property = (Method,Method,Field,MethodSymbol)
  type PropertySet = (String,Property)
  val SetterSuffix = "_$eq"

  override def toFieldEntries(targetClass: Class[_], from: FieldOption) : Array[FieldEntry] = {


    /*val sig = ScalaSigParser.parse(targetClass)
    
    val (setters,getters) = sig.get.symbols.collect({
      case m : MethodSymbol => {
        m.name -> m
      }
    }).partition(v => v._1.endsWith(SetterSuffix))

    val getterMap = getters.toMap

    val props = setters.map(s => s._1.substring(0,s._1.length - 4)).
      filter(fieldName => getterMap.contains(fieldName)).map(fieldName => {
      fieldName -> getterMap(fieldName)
    })*/

    val props = ScalaSigUtil.getAllPropGetters(targetClass)

    val propertySetSeq = toPropertySetSeq(targetClass,props)
    val indexed = indexing(propertySetSeq)

    indexed.map(convertToScalaFieldEntry(_))
  }


  def toPropertySetSeq(targetClass: Class[_],props : Seq[(String,MethodSymbol)]) : Seq[PropertySet] = {
    val methodMap = targetClass.getMethods().filter(isGetterOrSetter _).groupBy(_.getName)
    val fieldMap = targetClass.getDeclaredFields.map(f => f.getName -> f).toMap


    val propSets = props.map( p => {
      val getters = methodMap.get(p._1).map(_.toList).getOrElse(Nil)
      val setters = methodMap.get(p._1 + SetterSuffix).map(_.toList).getOrElse(Nil)
      if(getters.size < 1 || setters.size < 1){
        None
      }else if(getters.size == 1 && setters.size == 1){
        val getter = getters(0)
        val setter = setters(0)
        if(sameType_?(getter ,setter)){
          Some( p._1 -> (getter,setter,fieldMap.getOrElse(p._1,null),p._2))
        }else{
          None
        }
      }else {
        val validPairs = for(getter <- getters;
             setter <- setters if sameType_?(getter ,setter))
          yield p._1 -> (getter,setter,fieldMap.getOrElse(p._1,null),p._2)
        validPairs.headOption
      }
    }).collect({
      case Some(p) => p
    })

    propSets.filter( ps => {
      !hasAnnotation(ps,classOf[Ignore])
    })

  }

  def sameType_?(getter: Method, setter: Method) = {
    getter.getReturnType == setter.getParameterTypes()(0)
  }

  def isGetterOrSetter( method : Method) = {
    Modifier.isPublic(method.getModifiers) && !method.getName.startsWith("_") &&
    ( (
        method.getReturnType.getName == "void" &&
        method.getName.endsWith(SetterSuffix) &&
        method.getParameterTypes.length == 1
      ) || (
        method.getReturnType.getName != "void" &&
        method.getParameterTypes.length == 0
      ) )
  }


  def indexing(props: Seq[PropertySet]): Array[PropertySet] = {
    val indexed = new Array[PropertySet](props.size)

    var notIndexed: List[PropertySet] = Nil

    for (s <- props) {
      val i = getAnnotation(s, classOf[Index])
      if (i == null) {
        notIndexed = notIndexed :+ s
      } else {
        val index = i.value
        if (indexed(index) != null) {
          throw new TemplateBuildException("duplicated index: " + index);
        } else {
          try {
            indexed(index) = s
          } catch {
            case e: Exception => {
              throw new TemplateBuildException("invalid index: %s index must be 0 <= x < %s".format(index, indexed.length));
            }
          }
        }
      }
    }
    for (i <- 0 until indexed.length) {
      if (indexed(i) == null) {
        indexed(i) = notIndexed.head
        notIndexed = notIndexed.drop(1)
      }
    }


    indexed
  }

  def hasAnnotation[T <: JAnnotation](prop: PropertySet, classOfAnno: Class[T]): Boolean = {
    val getter = prop._2._1
    val setter = prop._2._2
    val field = prop._2._3
    getter.getAnnotation(classOfAnno) != null ||
      setter.getAnnotation(classOfAnno) != null || {
      if (field != null) field.getAnnotation(classOfAnno) != null
      else false
    }
  }

  def getAnnotation[T <: JAnnotation](prop: PropertySet, classOfAnno: Class[T]): T = {
    val getter = prop._2._1
    val setter = prop._2._2
    val field = prop._2._3



    val a = getter.getAnnotation(classOfAnno)
    if (a != null) {
      a
    } else {
      val b = setter.getAnnotation(classOfAnno)
      if (b != null) {
        b
      } else if (field != null) {
        field.getAnnotation(classOfAnno)
      } else {
        null.asInstanceOf[T]
      }
    }
  }

  def convertToScalaFieldEntry(propInfo: PropertySet) = {
    val getter = propInfo._2._1
    getter.getGenericReturnType match{
      case pt : ParameterizedType => {
        new ScalaFieldEntry(propInfo._1,
          readFieldOption(propInfo, FieldOption.OPTIONAL),
          getter.getReturnType,
          ScalaSigUtil.getReturnType(propInfo._2._4).get,
          propInfo._2._1,
          propInfo._2._2
        )
      }
      case t if t.asInstanceOf[Class[_]].getName == "scala.Enumeration$Value" => {
        new ScalaFieldEntry(propInfo._1,
          readFieldOption(propInfo, FieldOption.OPTIONAL),
          getter.getReturnType,
          ScalaSigUtil.getReturnType(propInfo._2._4).get,
          propInfo._2._1,
          propInfo._2._2
        )
      }
      case t => {
        new ScalaFieldEntry(propInfo._1,
          readFieldOption(propInfo, FieldOption.OPTIONAL),
          getter.getReturnType,
          t,
          propInfo._2._1,
          propInfo._2._2
        )
      }
    }
  }

  def readValueType(prop: PropertySet) = {
    prop._2._1.getReturnType
  }

  def readFieldOption(prop: PropertySet, implicitOption: FieldOption) = {
    if (hasAnnotation(prop, classOf[Optional])) {
      FieldOption.OPTIONAL
    } else if (hasAnnotation(prop, classOf[NotNullable])) {
      FieldOption.NOTNULLABLE
    } else if (hasAnnotation(prop, classOf[Ignore])) {
      FieldOption.IGNORE
    } else {
      if (readValueType(prop).isPrimitive) {
        FieldOption.NOTNULLABLE
      } else {
        FieldOption.OPTIONAL
      }
    }

  }
}