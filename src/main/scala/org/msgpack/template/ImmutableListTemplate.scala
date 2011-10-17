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
package org.msgpack.template

import org.msgpack._
import packer.Packer
import unpacker.Unpacker

;
/*
 * Created by IntelliJ IDEA.
 * User: takeshita
 * Date: 11/03/11
 * Time: 2:25
 */

class ImmutableListTemplate[T](elementTemplate : Template[T]) extends AbstractTemplate[List[T]]{


  def read(u: Unpacker, to: List[T], required: Boolean) : List[T] = {
    if(!required && u.trySkipNil){
      return null.asInstanceOf[List[T]]
    }
    val length = u.readArrayBegin()
    val seq = for(i <- 0 until length) yield  elementTemplate.read(u,null.asInstanceOf[T],required)
    u.readArrayEnd

    seq.toList
  }

  def write(packer: Packer, v: List[T], required: Boolean) : Unit = {
    if(v == null){
      if(required){
        throw new MessageTypeException("Attempted to write null")
      }
      packer.writeNil()
      return
    }

    packer.writeArrayBegin(v.size)
    v.foreach(e => {
      elementTemplate.write(packer,e,required)
    })
    packer.writeArrayEnd()

  }
}
