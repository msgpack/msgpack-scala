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
 * Create: 11/10/14 13:07
 */

class RichValue(messagePack : MessagePack,value : Value){

  def apply( index : Int) : Value = {
    if(value.isMapValue){
      apply(ValueFactory.createIntegerValue(index))
    }else{
      value.asArrayValue().get(index)
    }
  }
  def apply( value : Value) : Value = {
    value.asMapValue().get(value)
  }

  def toValueArray : Array[Value] = {
    value.asArrayValue().getElementArray()
  }
  def toValueList:List[Value] = {
    toValueArray.toList
  }

  def toValueMap: Map[Value,Value] = {
    Map(value.asMapValue().getKeyValueArray.sliding(2,2).map(p => p(0) -> p(1)).toSeq:_*)
  }

  def asArray[T](implicit manifest : Manifest[Array[T]]) : Array[T] = {
    messagePack.convert(value,manifest.erasure).asInstanceOf[Array[T]]
  }

  def asList[T](implicit manifest : Manifest[T]) : List[T] = {
    asArray(manifest.arrayManifest).toList
  }

  def as[T](implicit manifest : Manifest[T]) : T = {
    messagePack.convert(value,manifest.erasure.asInstanceOf[Class[T]])
  }

  def asMap[K,V](implicit keyManife : Manifest[K] , valueManife : Manifest[V]) : Map[K,V] = {
    val keyC = keyManife.erasure.asInstanceOf[Class[K]]
    val valueC = valueManife.erasure.asInstanceOf[Class[V]]
    Map(value.asMapValue().getKeyValueArray().sliding(2,2).map( v => {
      messagePack.convert(v(0),keyC) -> messagePack.convert(v(1),valueC)
    }).toSeq:_*)
  }
}