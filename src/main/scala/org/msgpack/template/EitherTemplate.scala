package org.msgpack.template

import org.msgpack.unpacker.Unpacker
import org.msgpack.packer.Packer
import org.msgpack.MessageTypeException

/**
 *
 * User: takeshita
 * Create: 12/04/21 3:56
 */

class EitherTemplate[L,R](leftTemplate : Template[L],rightTemplate : Template[R]) extends AbstractTemplate[Either[L,R]] {

  def write(pk: Packer, v: Either[L, R], required: Boolean) {

    if(v == null){
      if(required){
        throw new MessageTypeException("Attempted to write null")
      }
      pk.writeNil()
      return
    }
    pk.writeArrayBegin(3)
    v match{
      case Left(v) => {
        pk.write(false)
        leftTemplate.write(pk,v,false)
        pk.writeNil()
      }
      case Right(v) => {
        pk.write(true)
        pk.writeNil()
        rightTemplate.write(pk,v,false)
      }
    }
    pk.writeArrayEnd()

  }

  def read(u: Unpacker, to: Either[L, R], required: Boolean): Either[L, R] = {
    if(!required && u.trySkipNil){
      return null.asInstanceOf[Either[L,R]]
    }
    u.readArrayBegin()
    val v = if(u.readBoolean()){
      u.readNil()
      Right(rightTemplate.read(u,null.asInstanceOf[R],false))
    }else{
      val l = Left(leftTemplate.read(u,null.asInstanceOf[L],false))
      u.readNil()
      l
    }
    u.readArrayEnd()
    v
  }
}
