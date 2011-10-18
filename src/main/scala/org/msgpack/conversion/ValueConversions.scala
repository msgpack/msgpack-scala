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

  implicit def tuple2ToValue( t : Tuple2[Any,Any]) : Value = productToValue(t)
  implicit def tuple3ToValue( t : Tuple3[Any,Any,Any]) : Value = productToValue(t)
  implicit def tuple4ToValue( t : Tuple4[Any,Any,Any,Any]) : Value = productToValue(t)
  implicit def tuple5ToValue( t : Tuple5[Any,Any,Any,Any,Any]) : Value = productToValue(t)
  implicit def tuple6ToValue( t : Tuple6[Any,Any,Any,Any,Any,Any]) : Value = productToValue(t)
  implicit def tuple7ToValue( t : Tuple7[Any,Any,Any,Any,Any,Any,Any]) : Value = productToValue(t)
  implicit def tuple8ToValue( t : Tuple8[Any,Any,Any,Any,Any,Any,Any,Any]) : Value = productToValue(t)
  implicit def tuple9ToValue( t : Tuple9[Any,Any,Any,Any,Any,Any,Any,Any,Any]) : Value = productToValue(t)
  implicit def tuple10ToValue( t : Tuple10[Any,Any,Any,Any,Any,Any,Any,Any,Any,Any]) : Value = productToValue(t)
  implicit def tuple11ToValue( t : Tuple11[Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any]) : Value = productToValue(t)
  implicit def tuple12ToValue( t : Tuple12[Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any]) : Value = productToValue(t)
  implicit def tuple13ToValue( t : Tuple13[Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any]) : Value = productToValue(t)
  implicit def tuple14ToValue( t : Tuple14[Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any]) : Value = productToValue(t)
  implicit def tuple15ToValue( t : Tuple15[Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any]) : Value = productToValue(t)
  implicit def tuple16ToValue( t : Tuple16[Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any]) : Value = productToValue(t)
  implicit def tuple17ToValue( t : Tuple17[Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any]) : Value = productToValue(t)
  implicit def tuple18ToValue( t : Tuple18[Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any]) : Value = productToValue(t)
  implicit def tuple19ToValue( t : Tuple19[Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any]) : Value = productToValue(t)
  implicit def tuple20ToValue( t : Tuple20[Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any]) : Value = productToValue(t)
  implicit def tuple21ToValue( t : Tuple21[Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any]) : Value = productToValue(t)
  implicit def tuple22ToValue( t : Tuple22[Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any]) : Value = productToValue(t)

  def productToValue( p : Product) : Value = {
    val array = new Array[Value](p.productArity)
    var index = 0
    for( v <- p.productIterator){
      array(index) = objToValue(v); index += 1
    }
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
      //case p : Product => productToValue(p)
      case _ => messagePack.unconvert(v)
    }
  }



}

