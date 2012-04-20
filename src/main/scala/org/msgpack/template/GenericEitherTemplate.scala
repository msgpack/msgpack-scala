package org.msgpack.template

/**
 *
 * User: takeshita
 * Create: 12/04/21 4:02
 */

class GenericEitherTemplate extends GenericTemplate {
  def build(params: Array[Template[_]]) = {
    new EitherTemplate[Any,Any](params(0).asInstanceOf[Template[Any]],
      params(1).asInstanceOf[Template[Any]]).asInstanceOf[Template[Any]]
  }
}
