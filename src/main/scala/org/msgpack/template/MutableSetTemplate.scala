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

import org.msgpack.packer.Packer
import org.msgpack.unpacker.Unpacker
import org.msgpack.MessageTypeException
import scala.collection.mutable.LinkedHashSet
import scala.collection.mutable.HashSet
import scala.collection.mutable.BitSet
import scala.collection.mutable.Set
;

/*
 * Author: Tsuyoshi Ozawa
 */

abstract class MutableSetTemplate[V,T <: Set[V]](elementTemplate : Template[V])  extends AbstractTemplate[T]{


  def read(packer: Unpacker, to: T, required: Boolean) : T = {
    if(!required && packer.trySkipNil){
      return null.asInstanceOf[T]
    }

    var set : T = if(to == null){
      createNewSet()
    }else{
      to
    }
    val length = packer.readArrayBegin
    for(i <- 0 until length){
      set = (set + elementTemplate.read(packer,null.asInstanceOf[V])).asInstanceOf[T]
    }
    packer.readArrayEnd
    set
  }

  def write(packer: Packer, v: T, required: Boolean) : Unit = {
    if(v == null){
      if(required){
        throw new MessageTypeException("Attempted to write null")
      }
      packer.writeNil
      return
    }
    packer.writeArrayBegin(v.size)
    v.foreach(e => elementTemplate.write(packer,e))
    packer.writeArrayEnd()
  }

  def createNewSet() : T

}

// 2.10 or later.
//class SortedSetTemplate[V](elementTemplate : Template[V])
//  extends MutableSetTemplate[V,SortedSet[V]](elementTemplate){
//  def createNewSet() = SortedSet.empty
//}

class MutableSetCTemplate[V](elementTemplate : Template[V])
  extends MutableSetTemplate[V,Set[V]](elementTemplate){
  def createNewSet() = Set.empty
}

class LinkedHashSetTemplate[V](elementTemplate : Template[V])
  extends MutableSetTemplate[V,LinkedHashSet[V]](elementTemplate){
  def createNewSet() = LinkedHashSet.empty
}

class HashSetTemplate[V](elementTemplate : Template[V])
  extends MutableSetTemplate[V,HashSet[V]](elementTemplate){
  def createNewSet() = HashSet.empty
}
