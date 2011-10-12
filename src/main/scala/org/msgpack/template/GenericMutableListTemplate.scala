package org.msgpack.template

/**
 * 
 * User: takeshita
 * Create: 11/10/13 2:01
 */

class GenericMutableListTemplate[T <: MutableListTemplate[_,_]](implicit manifest : Manifest[T]) extends GenericTemplate{

  val constructor = {
    val c = manifest.erasure
    c.getConstructor(classOf[Template[_]])
  }

  def build(params: Array[Template[_]]) = {
    constructor.newInstance(params(0)).asInstanceOf[T]
  }
}