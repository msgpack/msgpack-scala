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

import annotation.{Ignore, NotNullable, Index, Message}

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

  // private var must set prefix _ph_( place holder)
  private var _ph_myNumber : Int = 0

  def myNumber : Int = _ph_myNumber
  def myNumber_=( v:  Int) : Unit = _ph_myNumber = v
}

@Message
class OverloadVars{

  private var _ph_id : Long = 0

  def id_=( v : String) = _ph_id = v.toLong
  def id_=( v : Long) = _ph_id = v
  def id : Long = _ph_id
  def id_=(obj : Any) = _ph_id = obj.toString.toLong
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