//
// MessagePack for Scala
//
// Copyright (C) 2009-2013 FURUHASHI Sadayuki
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

class ImmutableSetTemplate[T](elementTemplate : Template[T]) extends AbstractTemplate[Set[T]]{


  def read(u: Unpacker, to: Set[T], required: Boolean) : Set[T] = {
    if(!required && u.trySkipNil){
      return null.asInstanceOf[Set[T]]
    }
    val length = u.readArrayBegin()
    val set = for(i <- 0 until length) yield  elementTemplate.read(u,null.asInstanceOf[T],required)
    u.readArrayEnd

    set.toSet
  }

  def write(packer: Packer, v: Set[T], required: Boolean) : Unit = {
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
