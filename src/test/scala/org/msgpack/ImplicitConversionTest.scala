//
//  MessagePack for Scala
//
//  Copyright (C) 2009-2011 FURUHASHI Sadayuki
//
//     Licensed under the Apache License, Version 2.0 (the "License");
//     you may not use this file except in compliance with the License.
//     You may obtain a copy of the License at
//
//         http://www.apache.org/licenses/LICENSE-2.0
//
//     Unless required by applicable law or agreed to in writing, software
//     distributed under the License is distributed on an "AS IS" BASIS,
//     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//     See the License for the specific language governing permissions and
//     limitations under the License.
//

package org.msgpack

import `type`.Value
import org.junit.runner.RunWith
import org.specs.Specification
import org.specs.runner.{JUnit, JUnitSuiteRunner}

/**
 * 
 * User: takeshita
 * Create: 11/10/14 13:38
 */

@RunWith(classOf[JUnitSuiteRunner])
class ImplicitConversionTest extends Specification with JUnit{

  "Implicit conversions" should{
    "convert primitives" in{
      import ScalaMessagePack._

      println(List("","").getClass().hashCode)
      println(ScalaMessagePack.messagePack.lookup(List("","").getClass()))

      val v = List(1,23L,2 : Short , 4:Byte , 0.3f , 0.22 , false)
      val data = writeV(v)
      val decoded = readAsValue(data)

      val i : Int = decoded(0)
      i must_== v(0)
      val l : Long = decoded(1)
      l must_== v(1)
      val s : Short = decoded(2)
      s must_== v(2)
      val b : Byte = decoded(3)
      b must_== v(3)
      val f : Float = decoded(4)
      f must_== v(4)
      val d : Double = decoded(5)
      d must_== v(5)
      val bool : Boolean = decoded(6)
      bool must_== v(6)
    }
    "convert list,map" in{
      import ScalaMessagePack._

      val v = List(Map("hoge" -> 34,"fuga" -> false),List(1,2,3),List("a","b"))
      val data = writeV(v)
      val decoded = readAsValue(data)
      println(decoded)

      val map : Map[Value,Value] = decoded(0).toValueMap
      println(map)

      val i : Int = map("hoge")
      i must_== 34
      val b : Boolean = map("fuga")
      b must_== false

      val iList = decoded(1).asList[Int]
      val sList = decoded(2).asList[String]


      iList must_== v(1).asInstanceOf[List[Int]]
      sList must_== v(2).asInstanceOf[List[String]]


    }


  }

}