package org.msgpack.template

import org.msgpack.unpacker.Unpacker
import org.msgpack.packer.Packer

/**
 *
 * User: takeshita
 * Create: 12/04/21 3:23
 */

class GenericOptionTemplate extends GenericTemplate {

  def build(params: Array[Template[_]]) = {
    new OptionTemplate[Any](params(0).asInstanceOf[Template[Any]])
  }

}
