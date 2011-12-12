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
package org.msgpack

import org.junit.runner.RunWith
import org.specs.Specification
import org.specs.runner.{JUnit, JUnitSuiteRunner}
import org.specs.matcher.Matcher
import org.junit.Ignore
import java.util.{Date, Calendar}

/**
 * 
 * User: takeshita
 * Create: 11/10/13 0:44
 */

@RunWith(classOf[JUnitSuiteRunner])
class JavassistPatternTest extends PatternTestBase(ScalaMessagePack)

/**
 *
 */
@RunWith(classOf[JUnitSuiteRunner])
class ReflectionPatternTest extends PatternTestBase(ScalaMessagePackForReflection)

abstract class PatternTestBase(messagePack : ScalaMessagePackWrapper) extends Specification with JUnit{

  import messagePack._



  "PrimitiveTypes" should{
    "encode / decode" in{
      val o = new PrimitiveTypes
      o.byteVal = 33
      o.intVal = 5829
      o.shortVal = 232
      o.longVal = 238912847
      o.doubleVal = 230
      o.floatVal = 328219.32f

      checkOn(o,"intVal","byteVal","shortVal","longVal","doubleVal","floatVal")
    }
  }
  "CommonTypes" should{
    "encode / decode" in{
      val o = new CommonTypes
      o.string = "93mkodw978932jkj";
      o.date = new Date(12898472378L)
      o.calendar = Calendar.getInstance()
      checkOn(o,"string","date","calendar")

    }

  }

  /* don't support enumeration now
  "Enums" should{
    "encode / decode in unsafe template" in{
      val e = new Enums
      e.gender = Gender.Male
      e.country = null
      checkOn(e,"gender","country")
    }

  }
  */
  "Indexing" should{
    "index correctly" in{
      val o = new Indexing()
      o.one = 1
      o.two = 2
      o.three = 3
      val decoded = checkOn(o,"zero","one","two","three")

      // confirm field order
      val data = pack(o)
      val mirror = unpack[IndexingMirror](data)
      decoded must hasEqualProps(mirror).on("zero","one","two","three")
    }
  }

  "Inherit" should{
    "inherit parent class and trait" in{
      val o = new Inherit()
      o.targetClassName = "rin"
      o.rootTraitName = "mio"
      o.rootTraitNum = 89284
      o.rootName = "kudo"
      o.rootNum = 238492
      checkOn(o,"targetClassName","rootTraitName","rootTraitNum","rootName","rootNum")
    }
  }

  "CustomGetterSetter" should{
    "target custum prop" in{
      val o = new CustomGetterSetter
      o.myNumber = 382902
      checkOn(o,"myNumber")
    }
  }

  "Options" should{
    "throw error if name is null" in{
      val o = new Options
      o.name = null
      pack(o) must throwA[MessageTypeException]
    }
    "ignore @ignore" in{
      val o = new Options
      o.name = "sasasegawa sasami"
      o.ignoreNum = 290391
      val decode = checkOn(o,"name")
      decode.ignoreNum must_== new Options().ignoreNum // default value
    }
  }

  "WithCompanion" should{
    "encode / decode" in{
      checkOn(new WithCompanion("saegusa haruka"), "name")
    }
  }

  "ConstructorOverload" should{
    "encode / decode" in{
      checkOn(new ConstructorOverload("futaki kanata"), "name")
    }
  }

  "ReferSelfClass" should{
    "encode / decode" in{
      val o = new ReferSelfClass("top")
      o.myClass = new ReferSelfClass("myClass")
      o.myList = o.myList :+ new ReferSelfClass("myList")
      o.myMap = o.myMap + ("myMap" -> new ReferSelfClass("myMap"))
      val decode = checkOn(o,"name")

      decode.myClass must hasEqualProps(o.myClass).on("name")
      decode.myList(0) must hasEqualProps(o.myList(0)).on("name")
      decode.myMap("myMap") must hasEqualProps(o.myMap("myMap")).on("name")
    }

  }

  "Cycle" should{
    "encode / decode" in{
      checkOn(new CycleA,"name")
    }
  }



  def checkOn[T <: AnyRef](obj : T , propNames : String*)(implicit manifest : Manifest[T]) : T = {
    val data = pack(obj)
    val decode = unpack[T](data)
    decode must hasEqualProps(obj).on(propNames :_*)
    decode
  }

}

case class hasEqualProps[T <: AnyRef]( expected : T){


  def on( propNames : String*) = {
    new PropMatcher(propNames.toList)
  }

  class PropMatcher(propNames : List[String]) extends Matcher[AnyRef]{
    def apply(a: => AnyRef) : (Boolean,String,String) = {
      val actual : AnyRef = a

      for(propName <- propNames){
        val eV = getValue(expected,propName)
        val aV = getValue(actual,propName)
        if(eV != aV ){
          return (false,"","prop:%s expect ( %s ) but ( %s )".format(propName,eV , aV))
        }
      }
      (true,"ok","")
    }

    def getValue( obj : AnyRef, propName : String) = {
      obj.getClass.getMethod(propName).invoke(obj)
    }

  }

}