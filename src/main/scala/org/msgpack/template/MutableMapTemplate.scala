package org.msgpack.template;
/*
 * Created by IntelliJ IDEA.
 * User: takeshita
 * Date: 11/03/11
 * Time: 12:06
 */

import org.msgpack._
import packer.Packer
import scala.collection.JavaConverters._
import unpacker.Unpacker
import collection.mutable.{LinkedHashMap, ListMap, HashMap, Map => MMap}

abstract class MutableMapTemplate[K,V,T <: MMap[K,V]](keyTemplate : Template[K] , valueTemplate : Template[V])
  extends AbstractTemplate[T] {

  def read(unpacker: Unpacker, to: T, required: Boolean) : T = {
    if(!required && unpacker.trySkipNil){
      return null.asInstanceOf[T]
    }
    val map = if(to != null) to else createEmptyMap()
    val length = unpacker.readMapBegin
    for(i <- 0 until length){
      map +=( keyTemplate.read(unpacker,null.asInstanceOf[K],required) ->
        valueTemplate.read(unpacker,null.asInstanceOf[V],required))
    }
    unpacker.readMapEnd
    map
  }

  def write(packer: Packer, v: T, required: Boolean) : Unit = {
    if(v == null){
      if(required){
        throw new MessageTypeException("Attempted to write null")
      }
      packer.writeNil
      return
    }

    packer.writeMapBegin(v.size)
    for( p <- v){
      keyTemplate.write(packer,p._1,required)
      valueTemplate.write(packer,p._2,required)
    }
    packer.writeMapEnd

  }


  def createEmptyMap() : T
}


class MutableHashMapTemplate[K,V](keyTemplate : Template[K] , valueTemplate : Template[V])
  extends MutableMapTemplate[K,V,HashMap[K,V]](keyTemplate,valueTemplate ) {
  def createEmptyMap() = HashMap.empty
}

class MutableListMapTemplate[K,V](keyTemplate : Template[K] , valueTemplate : Template[V])
  extends MutableMapTemplate[K,V,ListMap[K,V]](keyTemplate,valueTemplate ) {
  def createEmptyMap() = ListMap.empty
}

class MutableLinkedHashMapTemplate[K,V](keyTemplate : Template[K] , valueTemplate : Template[V])
  extends MutableMapTemplate[K,V,LinkedHashMap[K,V]](keyTemplate,valueTemplate ) {
  def createEmptyMap() = LinkedHashMap.empty
}
