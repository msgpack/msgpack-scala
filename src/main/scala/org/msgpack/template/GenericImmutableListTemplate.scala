package org.msgpack.template

/**
 * 
 * User: takeshita
 * Create: 11/10/13 1:52
 */

class GenericImmutableListTemplate extends GenericTemplate{
  def build(params: Array[Template[_]]) = {
    new ImmutableListTemplate[Any](params(0).asInstanceOf[Template[Any]])
  }
}