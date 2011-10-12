package org.msgpack

import `type`.Value
import template._
import collection.mutable.{MutableList, LinkedList}
import collection.mutable.{Map => MMap, HashMap => MHashMap}
import java.io.InputStream
;
/*
 * Created by IntelliJ IDEA.
 * User: takeshita
 * Date: 11/03/10
 * Time: 1:34
 */

object ScalaMessagePack {

  val messagePack = new ScalaMessagePack()

  def write( obj : Any) : Array[Byte] = {
    messagePack.write(obj)
  }
  def writeT[T](obj : T)(implicit template : Template[T]) : Array[Byte] = {
    messagePack.write(obj,template)
  }

  def read[T]( data : Array[Byte])(implicit manifest : Manifest[T]) : T = {
    messagePack.read(data, manifest.erasure.asInstanceOf[Class[T]])
  }

  def read[T](data : InputStream)(implicit manifest : Manifest[T]) : T = {
    messagePack.read(data, manifest.erasure.asInstanceOf[Class[T]])
  }

  def readTo[T](data : Array[Byte], obj : T) : T = {
    messagePack.read(data,obj)
  }

  def readAsValue( data : Array[Byte]) : Value = {
    messagePack.read(data)
  }




}

class ScalaMessagePack extends MessagePack(new ScalaTemplateRegistry()){



}