//
// MessagePack for Scala
//
// Copyright (C) 2009-2013 FURUHASHI Sadayuki
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

import annotation.{Message, MessagePackMessage}
import collection.mutable.{Set => MutableSet, LinkedHashSet, HashSet}
import collection.immutable.{Set => ImmutableSet}


@MessagePackMessage
class ClassWithSet {
  var immutable : ImmutableSet[String] = ImmutableSet.empty;
  var mutable1 : MutableSet[String] = MutableSet.empty
  var mutable2 : LinkedHashSet[String] = LinkedHashSet.empty
  var mutable3 : HashSet[String] = HashSet.empty
}

@MessagePackMessage
class ClassWithPrimitiveTypeSet {
  var intSet : ImmutableSet[Int] = ImmutableSet.empty
  var longSet : ImmutableSet[Long] = ImmutableSet.empty
  var boolSet : ImmutableSet[Boolean] = ImmutableSet.empty
  var doubleSet : ImmutableSet[Double] = ImmutableSet.empty
  var floatSet : ImmutableSet[Float] = ImmutableSet.empty
}
