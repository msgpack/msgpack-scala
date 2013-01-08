package org.msgpack.template

import org.msgpack.unpacker.Unpacker
import org.msgpack.packer.Packer
import org.msgpack.`type`.ValueType

/**
 *
 * User: takeshita
 * Create: 12/04/21 3:27
 */

class OptionTemplate[T]( someTemplate : Template[T]) extends AbstractTemplate[Option[T]]{
  def write(pk: Packer, v: Option[T], required: Boolean) {
    v match{
      case Some(t) => {
        someTemplate.write(pk,t,true)
      }
      case None | null => {
        pk.writeNil()
      }
    }
  }

  def read(u: Unpacker, to: Option[T], required: Boolean): Option[T] = {
    if(u.getNextType == ValueType.NIL){
      u.readNil()
      None
    }else{
      val v = Some(someTemplate.read(u,null.asInstanceOf[T],false))
      v
    }
  }
}
