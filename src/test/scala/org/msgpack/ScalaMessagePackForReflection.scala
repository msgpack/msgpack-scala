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
package org.msgpack

import conversion.ValueConversions
import template.{ScalaTemplateBuilderChain, ScalaTemplateRegistry}

/**
 * 
 * User: takeshita
 * Create: 11/10/13 12:57
 */

object ScalaMessagePackForReflection extends ScalaMessagePackWrapper with ValueConversions{
  val messagePack = {
    new MessagePack(new ScalaTemplateRegistry(){
      override def createTemplateBuilderChain() = {
        new ScalaTemplateBuilderChain(this,true)
      }
    })
  }
}