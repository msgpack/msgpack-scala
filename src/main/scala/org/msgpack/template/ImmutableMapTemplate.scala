package org.msgpack.template

import org.msgpack._

import packer.Packer
import scala.collection.JavaConverters._
import unpacker.Unpacker

/*
 * Created by IntelliJ IDEA.
 * User: takeshita
 * Date: 11/03/11
 * Time: 11:11
 */

class ImmutableMapTemplate[Key,Value](keyTemplate : Template[Key] , valueTemplate : Template[Value])
  extends AbstractTemplate[Map[Key,Value]] {


  def read(unpacker: Unpacker, to: Map[Key, Value], required: Boolean) : Map[Key,Value] = {
    if(!required && unpacker.trySkipNil){
      return null.asInstanceOf[Map[Key,Value]]
    }

    val length = unpacker.readMapBegin
    val seq = for(i <- 0 until length ) yield (
      keyTemplate.read(unpacker,null.asInstanceOf[Key],required),
      valueTemplate.read(unpacker,null.asInstanceOf[Value],required))
    unpacker.readMapEnd
    Map(seq.toList :_*)
  }

  def write(packer: Packer, v: Map[Key, Value], required: Boolean) : Unit = {
    if(v == null){
      if(required){
        throw new MessageTypeException("Attempted to write null")
      }
      packer.writeNil()
      return
    }

    packer.writeMapBegin(v.size)
    for( p <- v){
      keyTemplate.write(packer,p._1,required)
      valueTemplate.write(packer,p._2,required)
    }
    packer.writeMapEnd
  }



}