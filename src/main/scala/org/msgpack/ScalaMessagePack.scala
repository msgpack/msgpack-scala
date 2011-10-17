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

import `type`.Value
import conversion.ValueConversions
import template._
import collection.mutable.{MutableList, LinkedList}
import collection.mutable.{Map => MMap, HashMap => MHashMap}
import java.io.InputStream
/*
 * The core object of scala message pack
 * You should import
 * {{{
 * import org.msgpack.ScalaMessagePack._
 * }}}
 * then you can pack / unpack easily
 * {{{
 * val packedData = write(object) // or pack(object)
 * val unpackedObject = read[TargetClass](packedData) // or unpack[TargetClass](packedData)
 * }}}
 *
 * User: takeshita
 * Date: 11/03/10
 * Time: 1:34
 */
object ScalaMessagePack extends ScalaMessagePackWrapper with ValueConversions{
  val messagePack = new ScalaMessagePack()
}

/**
 * Supply utility methods for MessagePack
 * Method names are changesd, because write and read method names often conflict when using filed importing
 *
 * name is changed becouse
 */
trait ScalaMessagePackWrapper{

  def messagePack : MessagePack

  def write( obj : Any) : Array[Byte] = {
    messagePack.write(obj)
  }

  /**
   * This is synonym for write.
   * You use this method when "write" name conflicts using field importing
   */
  def pack(obj : Any) : Array[Byte] = {
    messagePack.write(obj)
  }

  def writeT[T](obj : T)(implicit template : Template[T]) : Array[Byte] = {
    messagePack.write(obj,template)
  }

  def writeV(value : Value) : Array[Byte] = {
    messagePack.write(value)
  }

  def read[T]( data : Array[Byte])(implicit manifest : Manifest[T]) : T = {
    messagePack.read(data, manifest.erasure.asInstanceOf[Class[T]])
  }

  def read[T](data : InputStream)(implicit manifest : Manifest[T]) : T = {
    messagePack.read(data, manifest.erasure.asInstanceOf[Class[T]])
  }

  /**
   * This is synonym for read.
   */
  def unpack[T]( data : Array[Byte])(implicit manifest : Manifest[T]) : T = {
    messagePack.read(data, manifest.erasure.asInstanceOf[Class[T]])
  }

  /**
   * This is synonym for read.
   */
  def unpack[T](data : InputStream)(implicit manifest : Manifest[T]) : T = {
    messagePack.read(data, manifest.erasure.asInstanceOf[Class[T]])
  }

  def readTo[T](data : Array[Byte], obj : T) : T = {
    messagePack.read(data,obj)
  }
  def readTo[T](data : InputStream, obj : T) : T = {
    messagePack.read(data,obj)
  }

  def readAsValue( data : Array[Byte]) : Value = {
    messagePack.read(data)
  }
  def readAsValue( data : InputStream) : Value = {
    messagePack.read(data)
  }

  def convert[T](value : Value)(implicit manifest : Manifest[T]) = {
    messagePack.convert(value,manifest.erasure)
  }
}


/**
 * Message pack implementation for scala
 */
class ScalaMessagePack extends MessagePack(new ScalaTemplateRegistry()){



}