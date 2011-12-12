package org.msgpack.template

import org.msgpack._

import packer.Packer
import unpacker.Unpacker
import java.util.{Calendar, Date}

/*
 * Scala's enumeration template.
 * This tempalte is very
 * User: takeshita
 * Date: 11/03/11
 * Time: 11:11
 */

class ScalaEnumTemplate(enum : Enumeration) extends AbstractTemplate[Enumeration#Value] {
  def write(packer: Packer, v: Enumeration#Value, required: Boolean) : Unit = {
    if(v == null){
      if(required){
        throw new MessageTypeException("Attempted to write null")
      }
      packer.writeNil()
      return
    }
    packer.write(v.id)
  }

  def read(u: Unpacker, to: Enumeration#Value, required: Boolean): Enumeration#Value = {

    if(!required && u.trySkipNil){
      return null
    }
    try{
      enum.apply(u.readInt())
    }catch{
      case e : NoSuchElementException => {
        null
      }
    }
  }
}

/**
 *
 */
class ScalaSafeEnumTemplate(enum : Enumeration, default : Enumeration#Value) extends AbstractTemplate[Enumeration#Value] {
  def write(packer: Packer, v: Enumeration#Value, required: Boolean) : Unit = {
    if(v == null){
      if(required){
        throw new MessageTypeException("Attempted to write null")
      }
      packer.write(default.id)
      return
    }
    packer.write(v.id)
  }

  def read(u: Unpacker, to: Enumeration#Value, required: Boolean): Enumeration#Value = {

    if(!required && u.trySkipNil){
      return default
    }
    try{
      enum.apply(u.readInt())
    }catch{
      case e : NoSuchElementException => {
        default
      }
    }
  }
}
