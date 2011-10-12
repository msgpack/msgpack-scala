package org.msgpack.template.builder

import org.msgpack.template.FieldOption
import java.lang.reflect.{Method, Type}

/**
 * 
 * User: takeshita
 * Create: 11/10/12 17:36
 */

class ScalaFieldEntry(name : String,
                      option : FieldOption,
                      normalType : Class[_],
                      genericType : Type,
                      getter : Method,
                      setter : Method
                       ) extends FieldEntry(option){



  def getName = name

  def getType = normalType

  def getGenericType = genericType

  def primitive_? = {
    getType.isPrimitive
  }

  def nullable_? = {
    option != FieldOption.NOTNULLABLE
  }

  def available_? = {
    option != FieldOption.IGNORE
  }

  def optional_? = {
    option == FieldOption.OPTIONAL
  }

  def get(target: AnyRef) = {
    getter.invoke(target)
  }

  def set(target: AnyRef, value: AnyRef) = {
    setter.invoke(target,value)
  }
}