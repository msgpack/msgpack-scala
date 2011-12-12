package org.msgpack.template

import org.msgpack.packer.Packer
import org.msgpack.MessageTypeException
import org.msgpack.unpacker.Unpacker
import java.util.{Calendar, Date}

/*
 * Template for Calendar.
 * This template convert Calendar to long which is millisec from epoch
 * User: takeshita
 * Date: 11/03/11
 * Time: 11:11
 */
class CalendarTemplate extends AbstractTemplate[Calendar]{

  def write(packer: Packer, v: Calendar, required: Boolean) : Unit = {
    if(v == null){
      if(required){
        throw new MessageTypeException("Attempted to write null")
      }
      packer.writeNil()
    }else{
      packer.write(v.getTimeInMillis())
    }
  }

  def read(u: Unpacker, to: Calendar, required: Boolean): Calendar = {

    if(!required && u.trySkipNil){
      return null
    }
    val c = Calendar.getInstance()
    c.setTimeInMillis(u.readLong())
    c
  }
}