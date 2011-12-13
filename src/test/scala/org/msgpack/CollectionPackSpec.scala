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
import org.specs._
import org.specs.matcher._
import org.specs.runner.{ JUnitSuiteRunner, JUnit }
import scala.collection.mutable.{ListBuffer, LinkedList}

//import org.scalacheck.Gen

/**
 * Sample specification.
 * 
 * This specification can be executed with: scala -cp <your classpath=""> ${package}.SpecsTest
 * Or using maven: mvn test
 *
 * For more information on how to write or run specifications, please visit: http://code.google.com/p/specs.
 *
 */
@RunWith(classOf[JUnitSuiteRunner])
class CollectionPackTest extends Specification with JUnit  {

  "ScalaMessagePack" should {
    "pack scala-list" in {
      val c = new ClassWithList

      c.immutable = "z" :: List("a","b","c")
      c.mutable = LinkedList("a","b","d")
      c.mutable2 ++= List("gh","fjei")
      c.mutable3 = ListBuffer("fdk","fei")
      c.array = Array(3,3,2)
      c.immutableObj = List(SimpleClass(32),SimpleClass(488))



      val b = ScalaMessagePack.write(c)
      val des = ScalaMessagePack.read[ClassWithList](b)

      des must hasEqualProps(c).on("immutable","mutable2","mutable","mutable3")



    }
    "pack scala-primitive-type-list" in {
      val c = new ClassWithPrimitiveTypeList

      c.intList = List(1,5,2)
      c.longList = List(80L,23L,19381298L)
      c.boolList = List(false,true)
      c.floatList = List(2.0f,32f)
      c.doubleList = List(1.0,482.92)



      val b = ScalaMessagePack.write(c)
      val des = ScalaMessagePack.read[ClassWithPrimitiveTypeList](b)

      des must hasEqualProps(c).on("intList","longList","boolList","floatList","doubleList")



    }
    "pack scala-map" in {
      val c = new ClassWithMap
      c.immutable = Map("a" -> "hoge","b" -> "fuga","c" -> "hehe")
      c.mutable = scala.collection.mutable.Map("d" -> "oo" , "e" -> "aa")
      c.immutablePrimitive = Map("c" -> 2)

      val b = ScalaMessagePack.write(c)
      val des = ScalaMessagePack.read[ClassWithMap](b)

      des.immutable must be_==(c.immutable)
      des.mutable must be_==(c.mutable)
      des.immutablePrimitive must be_==(c.immutablePrimitive)

    }

  }

  "Tuple template" should{
    "convert / deconvert" in {

      val c = new ClassWithTuple()
      c.t1 = Tuple1[String]("fuga")
      c.t2 = ("a",1)
      c.t3 = ("b",5,0.4)

      val bytes = ScalaMessagePack.write(c)
      val des = ScalaMessagePack.read[ClassWithTuple](bytes)

      des.t1 must_== c.t1
      des.t2 must_== c.t2
      des.t3 must_== c.t3


    }
  }




}

