package org.msgpack.template

/**
 * 
 * User: takeshita
 * Create: 11/10/13 2:05
 */

class GenericMutableMapTemplate[T <: MutableMapTemplate[_,_,_]](implicit manifest : Manifest[T]) extends GenericTemplate{

  val constructor = {
    val c = manifest.erasure
    c.getConstructor(classOf[Template[_]],classOf[Template[_]])
  }

  def build(params: Array[Template[_]]) = {
    constructor.newInstance(params(0),params(1)).asInstanceOf[T]
  }
}