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

import annotation._
import java.util.{Calendar, Date}

/**
 * To test primitive types
 * User: takeshita
 * Create: 11/10/12 23:18
 */
@Message
class PrimitiveTypes{

  var intVal : Int = 1
  var longVal : Long = 2
  var shortVal : Short = 3
  var byteVal : Byte = 4
  var floatVal : Float = 5.0f
  var doubleVal : Double = 6.0
}

@Message
class CommonTypes{

  var string : String = ""
  var date : Date = new Date()
  var calendar : Calendar = Calendar.getInstance()

}




@Message
class Indexing{

  @Index(2)
  var two : Int = 0
  var one : Int = 0
  var three : Int = 0
  @Index(0)
  var zero : Int = 0
}

/**
 * For checking indexing
 */
@Message
class IndexingMirror{

  var zero : Int = 0
  var one : Int = 0
  var two : Int = 0
  var three : Int = 0
}


class RootClass{
  var rootName : String = "rootClass"
  var rootNum : Int = 234
}

trait RootTrait{
  var rootTraitName : String = "root"
  var rootTraitNum : Int = 23
}

@Message
class Inherit extends RootClass with RootTrait{

  var targetClassName : String = "targetClass"
}
@Message
class CustomGetterSetter{

  private var hoge = 2

  def myNumber : Int = hoge
  def myNumber_=( v:  Int) : Unit = hoge = v
}

@Message
class OverloadVars{

  private var hoge = ""

  def id_=( v : String) {hoge = v}
  def id_=( v : Long) {hoge = v.toString}
  def id : Long = hoge.toLong
  def id_=(obj : Any) = hoge = obj.toString
}

@Message
class Options{

  @NotNullable
  var name : String = ""

  @Ignore
  var ignoreNum : Int = 452
}

object WithCompanion{
  def apply() = new WithCompanion2()
}

@Message
class WithCompanion(var name : String){

}

@Message
class WithCompanion2 extends WithCompanion("hoge")

@Message
class ConstructorOverload(var name : String){

  def this() = this("fuga")
}

@Message
class ReferSelfClass(var name : String){

  def this() = this("")

  var myClass : ReferSelfClass = null
  var myList : List[ReferSelfClass] = List()
  var myMap : Map[String,ReferSelfClass] = Map.empty

}

@Message
class CycleA{
  var name : String = ""
  var cycleB : CycleB = new CycleB()
}

@Message
class CycleB{
  var cycleA : CycleA = null
  var cycleC : CycleC = new CycleC()
}

@Message
class CycleC{
  var cycleA : CycleA = null
}

@Message
class EnumFields{

  var enum1 : MyEnum.Value  = MyEnum.V1
  var enum2 : MyEnum2.Value = MyEnum2.V1
}


object MyEnum extends Enumeration{
  val V1 = Value
  val V2 = Value
}

class EnumExt extends Enumeration{

}

object MyEnum2 extends EnumExt{
  val V1 = Value(29)
  val V2 = Value(234)
}

@Message
class OptionFields{
  var o1 : Option[Int] = Some(23229)
  var o2 : Option[String] = Some("hoge")
  var l : Either[String,Long] = null
  var r : Either[Boolean,Double] = null

  var aa : String = "000"
}
