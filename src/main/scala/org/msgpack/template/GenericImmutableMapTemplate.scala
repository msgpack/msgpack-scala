package org.msgpack.template

/**
 * 
 * User: takeshita
 * Create: 11/10/13 1:56
 */

class GenericImmutableMapTemplate extends GenericTemplate{
  def build(params: Array[Template[_]]) = {
    new ImmutableMapTemplate[Any,Any](
      params(0).asInstanceOf[Template[Any]],
      params(1).asInstanceOf[Template[Any]])
  }
}