package org.msgpack.template

import org.msgpack.unpacker.Unpacker
import org.msgpack.packer.Packer

/**
 *
 * User: takeshita
 * Create: 12/04/21 2:48
 */

class EnumerationTemplate( e : Class[Enumeration]) extends AbstractTemplate[Enumeration#Value]{
  def write(pk: Packer, v: Enumeration#Value, required: Boolean) {
    pk.write(v.id)
  }

  def read(u: Unpacker, to: Enumeration#Value, required: Boolean): Enumeration#Value = {
    e.getMethod("apply",java.lang.Integer.TYPE).invoke(
      null,new java.lang.Integer(u.readInt())).asInstanceOf[Enumeration#Value]
  }
}
