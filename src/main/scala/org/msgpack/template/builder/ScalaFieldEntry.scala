//
// MessagePack for Scala
//
// Copyright (C) 2009-2011 FURUHASHI Sadayuki
//
//    Licensed under the Apache License, Version 2.0 (the "License");
//    you may not use this file except in compliance with the License.
//    You may obtain a copy of the License at
//
//        http://www.apache.org/licenses/LICENSE-2.0
//
//    Unless required by applicable law or agreed to in writing, software
//    distributed under the License is distributed on an "AS IS" BASIS,
//    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//    See the License for the specific language governing permissions and
//    limitations under the License.
//
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