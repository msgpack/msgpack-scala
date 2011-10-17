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

package org.msgpack.conversion

import org.msgpack.MessagePack
import org.msgpack.`type`.{ValueFactory, Value}

/**
 * 
 * User: takeshita
 * Create: 11/10/14 13:02
 */

/**
 * Defines Value implicit conversions
 */
trait ValueConversions{

  def messagePack : MessagePack
  // implicit Value conversions

  implicit def valueToByte(value : Value) : Byte = {
    value.asIntegerValue().getByte
  }
  implicit def valueToShort(value : Value) : Short = {
    value.asIntegerValue().getShort
  }
  implicit def valueToInt(value : Value) : Int = {
    value.asIntegerValue().getInt
  }
  implicit def valueToLong(value : Value) : Long = {
    value.asIntegerValue().getLong
  }
  implicit def valueToString(value : Value) : String = {
    value.asRawValue().getString()
  }
  implicit def valueToDouble(value : Value) : Double = {
    value.asFloatValue.getDouble
  }
  implicit def valueToFloat(value : Value) : Float = {
    value.asFloatValue.getFloat
  }
  implicit def valueToBool(value : Value) : Boolean = {
    value.asBooleanValue.getBoolean
  }
  /*
  implicit def valueToArray(value : Value) : Array[Value] = {
    value.asArrayValue().getElementArray
  }

  implicit def valueToMap(value : Value) : Map[Value,Value] = {
    Map(value.asMapValue().getKeyValueArray.map(p => {
      val a = p.asArrayValue().getElementArray()
      a(0) -> a(1)
    }):_*)
  }*/

  implicit def valueToRichValue(value : Value) : RichValue = {
    new RichValue(messagePack,value)
  }

  // to value

  implicit def strToValue( v : String) : Value = {
    ValueFactory.createRawValue(v)
  }
  implicit def intToValue( v : Int) : Value = {
    ValueFactory.createIntegerValue(v)
  }
  implicit def longToValue( v : Long) : Value = {
    ValueFactory.createIntegerValue(v)
  }
  implicit def byteToValue( v : Byte) : Value = {
    ValueFactory.createIntegerValue(v)
  }
  implicit def shortToValue( v : Short) : Value = {
    ValueFactory.createIntegerValue(v)
  }
  implicit def floatToValue( v : Float) : Value = {
    ValueFactory.createFloatValue(v)
  }
  implicit def doubleToValue( v : Double) : Value = {
    ValueFactory.createFloatValue(v)
  }
  implicit def boolToValue(v : Boolean) : Value = {
    ValueFactory.createBooleanValue(v)
  }

  implicit def arrayToValue( array : Array[Value]) : Value = {
    ValueFactory.createArrayValue(array)
  }

  implicit def listToValue( list : List[Any]) : Value = {
    val array = new Array[Value](list.size)
    var i = 0
    for(v <- list){
      array(i) = objToValue(v)
      i+=1
    }
    ValueFactory.createArrayValue(array)
  }

  implicit def mapToValue( map : Map[Any,Any]) : Value = {
    val array = new Array[Value](map.size * 2)
    var i = 0
    for(v <- map){
      array(i) = objToValue(v._1)
      array(i + 1) = objToValue(v._2)
      i+=2
    }
    ValueFactory.createMapValue()
  }

  def objToValue( v : Any) : Value = {
    v match{
      case i : Int => intToValue(i)
      case i : Short => shortToValue(i)
      case i : Long => longToValue(i)
      case i : Byte => byteToValue(i)
      case i : Float => floatToValue(i)
      case i : Double => doubleToValue(i)
      case i : Boolean => boolToValue(i)
      case v : Value => v.asInstanceOf[Value]
      case _ => messagePack.unconvert(v)
    }
  }



}

