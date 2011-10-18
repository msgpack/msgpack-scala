//
//  MessagePack for Scala
//
//  Copyright (C) 2009-2011 FURUHASHI Sadayuki
//
//     Licensed under the Apache License, Version 2.0 (the "License");
//     you may not use this file except in compliance with the License.
//     You may obtain a copy of the License at
//
//         http://www.apache.org/licenses/LICENSE-2.0
//
//     Unless required by applicable law or agreed to in writing, software
//     distributed under the License is distributed on an "AS IS" BASIS,
//     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//     See the License for the specific language governing permissions and
//     limitations under the License.
//

package org.msgpack.template

/**
 * 
 * User: takeshita
 * Create: 11/10/18 12:29
 */

abstract class GenericTupleTemplate  extends GenericTemplate{

}
class GenericTuple1Template  extends GenericTupleTemplate{

  def build(params: Array[Template[_]]) = {
    new Tuple1Template[Any](
      params(0).asInstanceOf[Template[Any]])
  }
}
class GenericTuple2Template  extends GenericTupleTemplate{

  def build(params: Array[Template[_]]) = {
    new Tuple2Template[Any,Any](
      params(0).asInstanceOf[Template[Any]],
      params(1).asInstanceOf[Template[Any]])
  }
}
class GenericTuple3Template  extends GenericTupleTemplate{

  def build(params: Array[Template[_]]) = {
    new Tuple3Template[Any,Any,Any](
      params(0).asInstanceOf[Template[Any]],
      params(1).asInstanceOf[Template[Any]],
      params(2).asInstanceOf[Template[Any]])
  }
}
class GenericTuple4Template  extends GenericTupleTemplate{

  def build(params: Array[Template[_]]) = {
    new Tuple4Template[Any,Any,Any,Any](
      params(0).asInstanceOf[Template[Any]],
      params(1).asInstanceOf[Template[Any]],
      params(2).asInstanceOf[Template[Any]],
      params(3).asInstanceOf[Template[Any]])
  }
}
class GenericTuple5Template  extends GenericTupleTemplate{

  def build(params: Array[Template[_]]) = {
    new Tuple5Template[Any,Any,Any,Any,Any](
      params(0).asInstanceOf[Template[Any]],
      params(1).asInstanceOf[Template[Any]],
      params(2).asInstanceOf[Template[Any]],
      params(3).asInstanceOf[Template[Any]],
      params(4).asInstanceOf[Template[Any]])
  }
}
class GenericTuple6Template  extends GenericTupleTemplate{

  def build(params: Array[Template[_]]) = {
    new Tuple6Template[Any,Any,Any,Any,Any,Any](
      params(0).asInstanceOf[Template[Any]],
      params(1).asInstanceOf[Template[Any]],
      params(2).asInstanceOf[Template[Any]],
      params(3).asInstanceOf[Template[Any]],
      params(4).asInstanceOf[Template[Any]],
      params(5).asInstanceOf[Template[Any]])
  }
}
class GenericTuple7Template  extends GenericTupleTemplate{

  def build(params: Array[Template[_]]) = {
    new Tuple7Template[Any,Any,Any,Any,Any,Any,Any](
      params(0).asInstanceOf[Template[Any]],
      params(1).asInstanceOf[Template[Any]],
      params(2).asInstanceOf[Template[Any]],
      params(3).asInstanceOf[Template[Any]],
      params(4).asInstanceOf[Template[Any]],
      params(5).asInstanceOf[Template[Any]],
      params(6).asInstanceOf[Template[Any]])
  }
}
class GenericTuple8Template  extends GenericTupleTemplate{

  def build(params: Array[Template[_]]) = {
    new Tuple8Template[Any,Any,Any,Any,Any,Any,Any,Any](
      params(0).asInstanceOf[Template[Any]],
      params(1).asInstanceOf[Template[Any]],
      params(2).asInstanceOf[Template[Any]],
      params(3).asInstanceOf[Template[Any]],
      params(4).asInstanceOf[Template[Any]],
      params(5).asInstanceOf[Template[Any]],
      params(6).asInstanceOf[Template[Any]],
      params(7).asInstanceOf[Template[Any]])
  }
}
class GenericTuple9Template  extends GenericTupleTemplate{

  def build(params: Array[Template[_]]) = {
    new Tuple9Template[Any,Any,Any,Any,Any,Any,Any,Any,Any](
      params(0).asInstanceOf[Template[Any]],
      params(1).asInstanceOf[Template[Any]],
      params(2).asInstanceOf[Template[Any]],
      params(3).asInstanceOf[Template[Any]],
      params(4).asInstanceOf[Template[Any]],
      params(5).asInstanceOf[Template[Any]],
      params(6).asInstanceOf[Template[Any]],
      params(7).asInstanceOf[Template[Any]],
      params(8).asInstanceOf[Template[Any]])
  }
}
class GenericTuple10Template  extends GenericTupleTemplate{

  def build(params: Array[Template[_]]) = {
    new Tuple10Template[Any,Any,Any,Any,Any,Any,Any,Any,Any,Any](
      params(0).asInstanceOf[Template[Any]],
      params(1).asInstanceOf[Template[Any]],
      params(2).asInstanceOf[Template[Any]],
      params(3).asInstanceOf[Template[Any]],
      params(4).asInstanceOf[Template[Any]],
      params(5).asInstanceOf[Template[Any]],
      params(6).asInstanceOf[Template[Any]],
      params(7).asInstanceOf[Template[Any]],
      params(8).asInstanceOf[Template[Any]],
      params(9).asInstanceOf[Template[Any]])
  }
}
class GenericTuple11Template  extends GenericTupleTemplate{

  def build(params: Array[Template[_]]) = {
    new Tuple11Template[Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any](
      params(0).asInstanceOf[Template[Any]],
      params(1).asInstanceOf[Template[Any]],
      params(2).asInstanceOf[Template[Any]],
      params(3).asInstanceOf[Template[Any]],
      params(4).asInstanceOf[Template[Any]],
      params(5).asInstanceOf[Template[Any]],
      params(6).asInstanceOf[Template[Any]],
      params(7).asInstanceOf[Template[Any]],
      params(8).asInstanceOf[Template[Any]],
      params(9).asInstanceOf[Template[Any]],
      params(10).asInstanceOf[Template[Any]])
  }
}
class GenericTuple12Template  extends GenericTupleTemplate{

  def build(params: Array[Template[_]]) = {
    new Tuple12Template[Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any](
      params(0).asInstanceOf[Template[Any]],
      params(1).asInstanceOf[Template[Any]],
      params(2).asInstanceOf[Template[Any]],
      params(3).asInstanceOf[Template[Any]],
      params(4).asInstanceOf[Template[Any]],
      params(5).asInstanceOf[Template[Any]],
      params(6).asInstanceOf[Template[Any]],
      params(7).asInstanceOf[Template[Any]],
      params(8).asInstanceOf[Template[Any]],
      params(9).asInstanceOf[Template[Any]],
      params(10).asInstanceOf[Template[Any]],
      params(11).asInstanceOf[Template[Any]])
  }
}
class GenericTuple13Template  extends GenericTupleTemplate{

  def build(params: Array[Template[_]]) = {
    new Tuple13Template[Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any](
      params(0).asInstanceOf[Template[Any]],
      params(1).asInstanceOf[Template[Any]],
      params(2).asInstanceOf[Template[Any]],
      params(3).asInstanceOf[Template[Any]],
      params(4).asInstanceOf[Template[Any]],
      params(5).asInstanceOf[Template[Any]],
      params(6).asInstanceOf[Template[Any]],
      params(7).asInstanceOf[Template[Any]],
      params(8).asInstanceOf[Template[Any]],
      params(9).asInstanceOf[Template[Any]],
      params(10).asInstanceOf[Template[Any]],
      params(11).asInstanceOf[Template[Any]],
      params(12).asInstanceOf[Template[Any]])
  }
}
class GenericTuple14Template  extends GenericTupleTemplate{

  def build(params: Array[Template[_]]) = {
    new Tuple14Template[Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any](
      params(0).asInstanceOf[Template[Any]],
      params(1).asInstanceOf[Template[Any]],
      params(2).asInstanceOf[Template[Any]],
      params(3).asInstanceOf[Template[Any]],
      params(4).asInstanceOf[Template[Any]],
      params(5).asInstanceOf[Template[Any]],
      params(6).asInstanceOf[Template[Any]],
      params(7).asInstanceOf[Template[Any]],
      params(8).asInstanceOf[Template[Any]],
      params(9).asInstanceOf[Template[Any]],
      params(10).asInstanceOf[Template[Any]],
      params(11).asInstanceOf[Template[Any]],
      params(12).asInstanceOf[Template[Any]],
      params(13).asInstanceOf[Template[Any]])
  }
}
class GenericTuple15Template  extends GenericTupleTemplate{

  def build(params: Array[Template[_]]) = {
    new Tuple15Template[Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any](
      params(0).asInstanceOf[Template[Any]],
      params(1).asInstanceOf[Template[Any]],
      params(2).asInstanceOf[Template[Any]],
      params(3).asInstanceOf[Template[Any]],
      params(4).asInstanceOf[Template[Any]],
      params(5).asInstanceOf[Template[Any]],
      params(6).asInstanceOf[Template[Any]],
      params(7).asInstanceOf[Template[Any]],
      params(8).asInstanceOf[Template[Any]],
      params(9).asInstanceOf[Template[Any]],
      params(10).asInstanceOf[Template[Any]],
      params(11).asInstanceOf[Template[Any]],
      params(12).asInstanceOf[Template[Any]],
      params(13).asInstanceOf[Template[Any]],
      params(14).asInstanceOf[Template[Any]])
  }
}
class GenericTuple16Template  extends GenericTupleTemplate{

  def build(params: Array[Template[_]]) = {
    new Tuple16Template[Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any](
      params(0).asInstanceOf[Template[Any]],
      params(1).asInstanceOf[Template[Any]],
      params(2).asInstanceOf[Template[Any]],
      params(3).asInstanceOf[Template[Any]],
      params(4).asInstanceOf[Template[Any]],
      params(5).asInstanceOf[Template[Any]],
      params(6).asInstanceOf[Template[Any]],
      params(7).asInstanceOf[Template[Any]],
      params(8).asInstanceOf[Template[Any]],
      params(9).asInstanceOf[Template[Any]],
      params(10).asInstanceOf[Template[Any]],
      params(11).asInstanceOf[Template[Any]],
      params(12).asInstanceOf[Template[Any]],
      params(13).asInstanceOf[Template[Any]],
      params(14).asInstanceOf[Template[Any]],
      params(15).asInstanceOf[Template[Any]])
  }
}
class GenericTuple17Template  extends GenericTupleTemplate{

  def build(params: Array[Template[_]]) = {
    new Tuple17Template[Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any](
      params(0).asInstanceOf[Template[Any]],
      params(1).asInstanceOf[Template[Any]],
      params(2).asInstanceOf[Template[Any]],
      params(3).asInstanceOf[Template[Any]],
      params(4).asInstanceOf[Template[Any]],
      params(5).asInstanceOf[Template[Any]],
      params(6).asInstanceOf[Template[Any]],
      params(7).asInstanceOf[Template[Any]],
      params(8).asInstanceOf[Template[Any]],
      params(9).asInstanceOf[Template[Any]],
      params(10).asInstanceOf[Template[Any]],
      params(11).asInstanceOf[Template[Any]],
      params(12).asInstanceOf[Template[Any]],
      params(13).asInstanceOf[Template[Any]],
      params(14).asInstanceOf[Template[Any]],
      params(15).asInstanceOf[Template[Any]],
      params(16).asInstanceOf[Template[Any]])
  }
}
class GenericTuple18Template  extends GenericTupleTemplate{

  def build(params: Array[Template[_]]) = {
    new Tuple18Template[Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any](
      params(0).asInstanceOf[Template[Any]],
      params(1).asInstanceOf[Template[Any]],
      params(2).asInstanceOf[Template[Any]],
      params(3).asInstanceOf[Template[Any]],
      params(4).asInstanceOf[Template[Any]],
      params(5).asInstanceOf[Template[Any]],
      params(6).asInstanceOf[Template[Any]],
      params(7).asInstanceOf[Template[Any]],
      params(8).asInstanceOf[Template[Any]],
      params(9).asInstanceOf[Template[Any]],
      params(10).asInstanceOf[Template[Any]],
      params(11).asInstanceOf[Template[Any]],
      params(12).asInstanceOf[Template[Any]],
      params(13).asInstanceOf[Template[Any]],
      params(14).asInstanceOf[Template[Any]],
      params(15).asInstanceOf[Template[Any]],
      params(16).asInstanceOf[Template[Any]],
      params(17).asInstanceOf[Template[Any]])
  }
}
class GenericTuple19Template  extends GenericTupleTemplate{

  def build(params: Array[Template[_]]) = {
    new Tuple19Template[Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any](
      params(0).asInstanceOf[Template[Any]],
      params(1).asInstanceOf[Template[Any]],
      params(2).asInstanceOf[Template[Any]],
      params(3).asInstanceOf[Template[Any]],
      params(4).asInstanceOf[Template[Any]],
      params(5).asInstanceOf[Template[Any]],
      params(6).asInstanceOf[Template[Any]],
      params(7).asInstanceOf[Template[Any]],
      params(8).asInstanceOf[Template[Any]],
      params(9).asInstanceOf[Template[Any]],
      params(10).asInstanceOf[Template[Any]],
      params(11).asInstanceOf[Template[Any]],
      params(12).asInstanceOf[Template[Any]],
      params(13).asInstanceOf[Template[Any]],
      params(14).asInstanceOf[Template[Any]],
      params(15).asInstanceOf[Template[Any]],
      params(16).asInstanceOf[Template[Any]],
      params(17).asInstanceOf[Template[Any]],
      params(18).asInstanceOf[Template[Any]])
  }
}
class GenericTuple20Template  extends GenericTupleTemplate{

  def build(params: Array[Template[_]]) = {
    new Tuple20Template[Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any](
      params(0).asInstanceOf[Template[Any]],
      params(1).asInstanceOf[Template[Any]],
      params(2).asInstanceOf[Template[Any]],
      params(3).asInstanceOf[Template[Any]],
      params(4).asInstanceOf[Template[Any]],
      params(5).asInstanceOf[Template[Any]],
      params(6).asInstanceOf[Template[Any]],
      params(7).asInstanceOf[Template[Any]],
      params(8).asInstanceOf[Template[Any]],
      params(9).asInstanceOf[Template[Any]],
      params(10).asInstanceOf[Template[Any]],
      params(11).asInstanceOf[Template[Any]],
      params(12).asInstanceOf[Template[Any]],
      params(13).asInstanceOf[Template[Any]],
      params(14).asInstanceOf[Template[Any]],
      params(15).asInstanceOf[Template[Any]],
      params(16).asInstanceOf[Template[Any]],
      params(17).asInstanceOf[Template[Any]],
      params(18).asInstanceOf[Template[Any]],
      params(19).asInstanceOf[Template[Any]])
  }
}
class GenericTuple21Template  extends GenericTupleTemplate{

  def build(params: Array[Template[_]]) = {
    new Tuple21Template[Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any](
      params(0).asInstanceOf[Template[Any]],
      params(1).asInstanceOf[Template[Any]],
      params(2).asInstanceOf[Template[Any]],
      params(3).asInstanceOf[Template[Any]],
      params(4).asInstanceOf[Template[Any]],
      params(5).asInstanceOf[Template[Any]],
      params(6).asInstanceOf[Template[Any]],
      params(7).asInstanceOf[Template[Any]],
      params(8).asInstanceOf[Template[Any]],
      params(9).asInstanceOf[Template[Any]],
      params(10).asInstanceOf[Template[Any]],
      params(11).asInstanceOf[Template[Any]],
      params(12).asInstanceOf[Template[Any]],
      params(13).asInstanceOf[Template[Any]],
      params(14).asInstanceOf[Template[Any]],
      params(15).asInstanceOf[Template[Any]],
      params(16).asInstanceOf[Template[Any]],
      params(17).asInstanceOf[Template[Any]],
      params(18).asInstanceOf[Template[Any]],
      params(19).asInstanceOf[Template[Any]],
      params(20).asInstanceOf[Template[Any]])
  }
}
class GenericTuple22Template  extends GenericTupleTemplate{

  def build(params: Array[Template[_]]) = {
    new Tuple22Template[Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any,Any](
      params(0).asInstanceOf[Template[Any]],
      params(1).asInstanceOf[Template[Any]],
      params(2).asInstanceOf[Template[Any]],
      params(3).asInstanceOf[Template[Any]],
      params(4).asInstanceOf[Template[Any]],
      params(5).asInstanceOf[Template[Any]],
      params(6).asInstanceOf[Template[Any]],
      params(7).asInstanceOf[Template[Any]],
      params(8).asInstanceOf[Template[Any]],
      params(9).asInstanceOf[Template[Any]],
      params(10).asInstanceOf[Template[Any]],
      params(11).asInstanceOf[Template[Any]],
      params(12).asInstanceOf[Template[Any]],
      params(13).asInstanceOf[Template[Any]],
      params(14).asInstanceOf[Template[Any]],
      params(15).asInstanceOf[Template[Any]],
      params(16).asInstanceOf[Template[Any]],
      params(17).asInstanceOf[Template[Any]],
      params(18).asInstanceOf[Template[Any]],
      params(19).asInstanceOf[Template[Any]],
      params(20).asInstanceOf[Template[Any]],
      params(21).asInstanceOf[Template[Any]])
  }
}