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

import org.msgpack.packer.Packer
import org.msgpack.unpacker.Unpacker
import org.msgpack.MessageTypeException
import collection.mutable.{Seq => MSeq,DoubleLinkedList, MutableList, LinearSeq, LinkedList,ListBuffer}
;
/*
 * Created by IntelliJ IDEA.
 * User: takeshita
 * Date: 11/03/11
 * Time: 2:37
 */

abstract class MutableListTemplate[V,T <: MSeq[V]](elementTemplate : Template[V])  extends AbstractTemplate[T]{


  def read(packer: Unpacker, to: T, required: Boolean) : T = {
    if(!required && packer.trySkipNil){
      return null.asInstanceOf[T]
    }


    var list : T = if(to == null){
      createNewList()
    }else{
      to
    }
    val length = packer.readArrayBegin
    for(i <- 0 until length){
      list = (list :+ elementTemplate.read(packer,null.asInstanceOf[V])).asInstanceOf[T]
    }
    packer.readArrayEnd
    list
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

  def createNewList() : T

}

class LinkedListTemplate[V](elementTemplate : Template[V])
  extends MutableListTemplate[V,LinkedList[V]](elementTemplate){
  def createNewList() = LinkedList.empty
}
class MutableListCTemplate[V](elementTemplate : Template[V])
  extends MutableListTemplate[V,MutableList[V]](elementTemplate){
  def createNewList() = {
    MutableList.empty
  }
}
class DoubleLinkedListTemplate[V](elementTemplate : Template[V])
  extends MutableListTemplate[V,DoubleLinkedList[V]](elementTemplate){
  def createNewList() = {
    DoubleLinkedList.empty
  }
}

class ListBufferTemplate[V](elementTemplate : Template[V])
  extends MutableListTemplate[V,ListBuffer[V]](elementTemplate){
  def createNewList() = {
    ListBuffer.empty
  }
}