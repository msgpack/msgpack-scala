package org.msgpack.template

import org.msgpack.unpacker.Unpacker
import org.msgpack.packer.Packer

/**
 *
 * User: takeshita
 * Create: 12/04/21 3:27
 */

class OptionTemplate[T]( someTemplate : Template[T]) extends AbstractTemplate[Option[T]]{
  def write(pk: Packer, v: Option[T], required: Boolean) {
    v match{
      case Some(t) => {
        pk.writeArrayBegin(2)
        pk.write(true)
        someTemplate.write(pk,t,true)
        pk.writeArrayEnd()
      }
      case None | null => {
        pk.writeArrayBegin(2)
        pk.write(false)
        pk.writeNil()
        pk.writeArrayEnd()
      }
    }
  }

  def read(u: Unpacker, to: Option[T], required: Boolean): Option[T] = {
    u.readArrayBegin()
    val op = if (u.readBoolean()){
      Some(someTemplate.read(u,null.asInstanceOf[T],false))
    }else{
      u.readNil()
      None
    }
    u.readArrayEnd()
    op
  }
}
