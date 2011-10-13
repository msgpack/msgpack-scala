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
package org.msgpack;
/*
 * Created by IntelliJ IDEA.
 * User: takeshita
 * Date: 11/03/11
 * Time: 2:13
 */

import annotation.MessagePackMessage
import collection.mutable.{ListBuffer, MutableList, LinkedList}

@MessagePackMessage
class ClassWithList {
  var immutable : List[String] = Nil

  var mutable : LinkedList[String] = LinkedList.empty

  var mutable2 : MutableList[String] = new MutableList

  var mutable3 : ListBuffer[String] = ListBuffer.empty

  //var tuple2 : (String,String) = (null,null)
}