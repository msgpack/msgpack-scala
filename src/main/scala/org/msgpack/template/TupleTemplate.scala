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

import org.msgpack.packer.Packer
import org.msgpack.MessageTypeException
import org.msgpack.unpacker.Unpacker

/**
 * 
 * User: takeshita
 * Create: 11/10/18 11:40
 */

abstract class TupleTemplate[T <: Product](templates : Template[_]*) extends AbstractTemplate[T]{
  def read(u: Unpacker, to: T, required: Boolean) : T = {
    if(!required && u.trySkipNil){
      return null.asInstanceOf[T]
    }
    val length = u.readArrayBegin()
    val tuple = readTuple(u,required)
    u.readArrayEnd
    tuple
  }
  def readTuple(u: Unpacker, required: Boolean) : T
  def write(packer: Packer, v: T, required: Boolean) : Unit = {
    if(v == null){
      if(required){
        throw new MessageTypeException("Attempted to write null")
      }
      packer.writeNil()
      return
    }

    var index = 0
    packer.writeArrayBegin(v.productArity)
    for( i <- v.productIterator){
      templates(index).asInstanceOf[Template[Any]].write(packer,i,required)
      index += 1
    }
    packer.writeArrayEnd()
  }
}

class Tuple1Template[T1]( t1 : Template[T1]) extends TupleTemplate[Tuple1[T1]]( t1){
  def readTuple(u: Unpacker, required: Boolean) = {
    Tuple1[T1](t1.read(u,null.asInstanceOf[T1],required))
  }
}

class Tuple2Template[T1,T2]( t1 : Template[T1],t2 : Template[T2]) extends TupleTemplate[Tuple2[T1,T2]]( t1,t2){
  def readTuple(u: Unpacker, required: Boolean) = {
    (t1.read(u,null.asInstanceOf[T1],required),
      t2.read(u,null.asInstanceOf[T2],required))
  }
}

class Tuple3Template[T1,T2,T3]( t1 : Template[T1],t2 : Template[T2],t3 : Template[T3]) extends TupleTemplate[Tuple3[T1,T2,T3]]( t1,t2,t3){
  def readTuple(u: Unpacker, required: Boolean) = {
    (t1.read(u,null.asInstanceOf[T1],required),
      t2.read(u,null.asInstanceOf[T2],required),
      t3.read(u,null.asInstanceOf[T3],required))
  }
}

class Tuple4Template[T1,T2,T3,T4]( t1 : Template[T1],t2 : Template[T2],t3 : Template[T3],t4 : Template[T4]) extends TupleTemplate[Tuple4[T1,T2,T3,T4]]( t1,t2,t3,t4){
  def readTuple(u: Unpacker, required: Boolean) = {
    (t1.read(u,null.asInstanceOf[T1],required),
      t2.read(u,null.asInstanceOf[T2],required),
      t3.read(u,null.asInstanceOf[T3],required),
      t4.read(u,null.asInstanceOf[T4],required))
  }
}
class Tuple5Template[T1,T2,T3,T4,T5]( t1 : Template[T1],t2 : Template[T2],t3 : Template[T3],t4 : Template[T4],t5 : Template[T5]) extends TupleTemplate[Tuple5[T1,T2,T3,T4,T5]]( t1,t2,t3,t4,t5){
  def readTuple(u: Unpacker, required: Boolean) = {
    (t1.read(u,null.asInstanceOf[T1],required),
      t2.read(u,null.asInstanceOf[T2],required),
      t3.read(u,null.asInstanceOf[T3],required),
      t4.read(u,null.asInstanceOf[T4],required),
      t5.read(u,null.asInstanceOf[T5],required))
  }
}
class Tuple6Template[T1,T2,T3,T4,T5,T6]( t1 : Template[T1],t2 : Template[T2],t3 : Template[T3],t4 : Template[T4],t5 : Template[T5],t6 : Template[T6]) extends TupleTemplate[Tuple6[T1,T2,T3,T4,T5,T6]]( t1,t2,t3,t4,t5,t6){
  def readTuple(u: Unpacker, required: Boolean) = {
    (t1.read(u,null.asInstanceOf[T1],required),
      t2.read(u,null.asInstanceOf[T2],required),
      t3.read(u,null.asInstanceOf[T3],required),
      t4.read(u,null.asInstanceOf[T4],required),
      t5.read(u,null.asInstanceOf[T5],required),
      t6.read(u,null.asInstanceOf[T6],required))
  }
}
class Tuple7Template[T1,T2,T3,T4,T5,T6,T7]( t1 : Template[T1],t2 : Template[T2],t3 : Template[T3],t4 : Template[T4],t5 : Template[T5],t6 : Template[T6],t7 : Template[T7]) extends TupleTemplate[Tuple7[T1,T2,T3,T4,T5,T6,T7]]( t1,t2,t3,t4,t5,t6,t7){
  def readTuple(u: Unpacker, required: Boolean) = {
    (t1.read(u,null.asInstanceOf[T1],required),
      t2.read(u,null.asInstanceOf[T2],required),
      t3.read(u,null.asInstanceOf[T3],required),
      t4.read(u,null.asInstanceOf[T4],required),
      t5.read(u,null.asInstanceOf[T5],required),
      t6.read(u,null.asInstanceOf[T6],required),
      t7.read(u,null.asInstanceOf[T7],required))
  }
}
class Tuple8Template[T1,T2,T3,T4,T5,T6,T7,T8]( t1 : Template[T1],t2 : Template[T2],t3 : Template[T3],t4 : Template[T4],t5 : Template[T5],t6 : Template[T6],t7 : Template[T7],t8 : Template[T8]) extends TupleTemplate[Tuple8[T1,T2,T3,T4,T5,T6,T7,T8]]( t1,t2,t3,t4,t5,t6,t7,t8){
  def readTuple(u: Unpacker, required: Boolean) = {
    (t1.read(u,null.asInstanceOf[T1],required),
      t2.read(u,null.asInstanceOf[T2],required),
      t3.read(u,null.asInstanceOf[T3],required),
      t4.read(u,null.asInstanceOf[T4],required),
      t5.read(u,null.asInstanceOf[T5],required),
      t6.read(u,null.asInstanceOf[T6],required),
      t7.read(u,null.asInstanceOf[T7],required),
      t8.read(u,null.asInstanceOf[T8],required))
  }
}
class Tuple9Template[T1,T2,T3,T4,T5,T6,T7,T8,T9]( t1 : Template[T1],t2 : Template[T2],t3 : Template[T3],t4 : Template[T4],t5 : Template[T5],t6 : Template[T6],t7 : Template[T7],t8 : Template[T8],t9 : Template[T9]) extends TupleTemplate[Tuple9[T1,T2,T3,T4,T5,T6,T7,T8,T9]]( t1,t2,t3,t4,t5,t6,t7,t8,t9){
  def readTuple(u: Unpacker, required: Boolean) = {
    (t1.read(u,null.asInstanceOf[T1],required),
      t2.read(u,null.asInstanceOf[T2],required),
      t3.read(u,null.asInstanceOf[T3],required),
      t4.read(u,null.asInstanceOf[T4],required),
      t5.read(u,null.asInstanceOf[T5],required),
      t6.read(u,null.asInstanceOf[T6],required),
      t7.read(u,null.asInstanceOf[T7],required),
      t8.read(u,null.asInstanceOf[T8],required),
      t9.read(u,null.asInstanceOf[T9],required))
  }
}
class Tuple10Template[T1,T2,T3,T4,T5,T6,T7,T8,T9,T10]( t1 : Template[T1],t2 : Template[T2],t3 : Template[T3],t4 : Template[T4],t5 : Template[T5],t6 : Template[T6],t7 : Template[T7],t8 : Template[T8],t9 : Template[T9],t10 : Template[T10]) extends TupleTemplate[Tuple10[T1,T2,T3,T4,T5,T6,T7,T8,T9,T10]]( t1,t2,t3,t4,t5,t6,t7,t8,t9,t10){
  def readTuple(u: Unpacker, required: Boolean) = {
    (t1.read(u,null.asInstanceOf[T1],required),
      t2.read(u,null.asInstanceOf[T2],required),
      t3.read(u,null.asInstanceOf[T3],required),
      t4.read(u,null.asInstanceOf[T4],required),
      t5.read(u,null.asInstanceOf[T5],required),
      t6.read(u,null.asInstanceOf[T6],required),
      t7.read(u,null.asInstanceOf[T7],required),
      t8.read(u,null.asInstanceOf[T8],required),
      t9.read(u,null.asInstanceOf[T9],required),
      t10.read(u,null.asInstanceOf[T10],required))
  }
}
class Tuple11Template[T1,T2,T3,T4,T5,T6,T7,T8,T9,T10,T11]( t1 : Template[T1],t2 : Template[T2],t3 : Template[T3],t4 : Template[T4],t5 : Template[T5],t6 : Template[T6],t7 : Template[T7],t8 : Template[T8],t9 : Template[T9],t10 : Template[T10],t11 : Template[T11]) extends TupleTemplate[Tuple11[T1,T2,T3,T4,T5,T6,T7,T8,T9,T10,T11]]( t1,t2,t3,t4,t5,t6,t7,t8,t9,t10,t11){
  def readTuple(u: Unpacker, required: Boolean) = {
    (t1.read(u,null.asInstanceOf[T1],required),
      t2.read(u,null.asInstanceOf[T2],required),
      t3.read(u,null.asInstanceOf[T3],required),
      t4.read(u,null.asInstanceOf[T4],required),
      t5.read(u,null.asInstanceOf[T5],required),
      t6.read(u,null.asInstanceOf[T6],required),
      t7.read(u,null.asInstanceOf[T7],required),
      t8.read(u,null.asInstanceOf[T8],required),
      t9.read(u,null.asInstanceOf[T9],required),
      t10.read(u,null.asInstanceOf[T10],required),
      t11.read(u,null.asInstanceOf[T11],required))
  }
}
class Tuple12Template[T1,T2,T3,T4,T5,T6,T7,T8,T9,T10,T11,T12]( t1 : Template[T1],t2 : Template[T2],t3 : Template[T3],t4 : Template[T4],t5 : Template[T5],t6 : Template[T6],t7 : Template[T7],t8 : Template[T8],t9 : Template[T9],t10 : Template[T10],t11 : Template[T11],t12 : Template[T12]) extends TupleTemplate[Tuple12[T1,T2,T3,T4,T5,T6,T7,T8,T9,T10,T11,T12]]( t1,t2,t3,t4,t5,t6,t7,t8,t9,t10,t11,t12){
  def readTuple(u: Unpacker, required: Boolean) = {
    (t1.read(u,null.asInstanceOf[T1],required),
      t2.read(u,null.asInstanceOf[T2],required),
      t3.read(u,null.asInstanceOf[T3],required),
      t4.read(u,null.asInstanceOf[T4],required),
      t5.read(u,null.asInstanceOf[T5],required),
      t6.read(u,null.asInstanceOf[T6],required),
      t7.read(u,null.asInstanceOf[T7],required),
      t8.read(u,null.asInstanceOf[T8],required),
      t9.read(u,null.asInstanceOf[T9],required),
      t10.read(u,null.asInstanceOf[T10],required),
      t11.read(u,null.asInstanceOf[T11],required),
      t12.read(u,null.asInstanceOf[T12],required))
  }
}
class Tuple13Template[T1,T2,T3,T4,T5,T6,T7,T8,T9,T10,T11,T12,T13]( t1 : Template[T1],t2 : Template[T2],t3 : Template[T3],t4 : Template[T4],t5 : Template[T5],t6 : Template[T6],t7 : Template[T7],t8 : Template[T8],t9 : Template[T9],t10 : Template[T10],t11 : Template[T11],t12 : Template[T12],t13 : Template[T13]) extends TupleTemplate[Tuple13[T1,T2,T3,T4,T5,T6,T7,T8,T9,T10,T11,T12,T13]]( t1,t2,t3,t4,t5,t6,t7,t8,t9,t10,t11,t12,t13){
  def readTuple(u: Unpacker, required: Boolean) = {
    (t1.read(u,null.asInstanceOf[T1],required),
      t2.read(u,null.asInstanceOf[T2],required),
      t3.read(u,null.asInstanceOf[T3],required),
      t4.read(u,null.asInstanceOf[T4],required),
      t5.read(u,null.asInstanceOf[T5],required),
      t6.read(u,null.asInstanceOf[T6],required),
      t7.read(u,null.asInstanceOf[T7],required),
      t8.read(u,null.asInstanceOf[T8],required),
      t9.read(u,null.asInstanceOf[T9],required),
      t10.read(u,null.asInstanceOf[T10],required),
      t11.read(u,null.asInstanceOf[T11],required),
      t12.read(u,null.asInstanceOf[T12],required),
      t13.read(u,null.asInstanceOf[T13],required))
  }
}
class Tuple14Template[T1,T2,T3,T4,T5,T6,T7,T8,T9,T10,T11,T12,T13,T14]( t1 : Template[T1],t2 : Template[T2],t3 : Template[T3],t4 : Template[T4],t5 : Template[T5],t6 : Template[T6],t7 : Template[T7],t8 : Template[T8],t9 : Template[T9],t10 : Template[T10],t11 : Template[T11],t12 : Template[T12],t13 : Template[T13],t14 : Template[T14]) extends TupleTemplate[Tuple14[T1,T2,T3,T4,T5,T6,T7,T8,T9,T10,T11,T12,T13,T14]]( t1,t2,t3,t4,t5,t6,t7,t8,t9,t10,t11,t12,t13,t14){
  def readTuple(u: Unpacker, required: Boolean) = {
    (t1.read(u,null.asInstanceOf[T1],required),
      t2.read(u,null.asInstanceOf[T2],required),
      t3.read(u,null.asInstanceOf[T3],required),
      t4.read(u,null.asInstanceOf[T4],required),
      t5.read(u,null.asInstanceOf[T5],required),
      t6.read(u,null.asInstanceOf[T6],required),
      t7.read(u,null.asInstanceOf[T7],required),
      t8.read(u,null.asInstanceOf[T8],required),
      t9.read(u,null.asInstanceOf[T9],required),
      t10.read(u,null.asInstanceOf[T10],required),
      t11.read(u,null.asInstanceOf[T11],required),
      t12.read(u,null.asInstanceOf[T12],required),
      t13.read(u,null.asInstanceOf[T13],required),
      t14.read(u,null.asInstanceOf[T14],required))
  }
}
class Tuple15Template[T1,T2,T3,T4,T5,T6,T7,T8,T9,T10,T11,T12,T13,T14,T15]( t1 : Template[T1],t2 : Template[T2],t3 : Template[T3],t4 : Template[T4],t5 : Template[T5],t6 : Template[T6],t7 : Template[T7],t8 : Template[T8],t9 : Template[T9],t10 : Template[T10],t11 : Template[T11],t12 : Template[T12],t13 : Template[T13],t14 : Template[T14],t15 : Template[T15]) extends TupleTemplate[Tuple15[T1,T2,T3,T4,T5,T6,T7,T8,T9,T10,T11,T12,T13,T14,T15]]( t1,t2,t3,t4,t5,t6,t7,t8,t9,t10,t11,t12,t13,t14,t15){
  def readTuple(u: Unpacker, required: Boolean) = {
    (t1.read(u,null.asInstanceOf[T1],required),
      t2.read(u,null.asInstanceOf[T2],required),
      t3.read(u,null.asInstanceOf[T3],required),
      t4.read(u,null.asInstanceOf[T4],required),
      t5.read(u,null.asInstanceOf[T5],required),
      t6.read(u,null.asInstanceOf[T6],required),
      t7.read(u,null.asInstanceOf[T7],required),
      t8.read(u,null.asInstanceOf[T8],required),
      t9.read(u,null.asInstanceOf[T9],required),
      t10.read(u,null.asInstanceOf[T10],required),
      t11.read(u,null.asInstanceOf[T11],required),
      t12.read(u,null.asInstanceOf[T12],required),
      t13.read(u,null.asInstanceOf[T13],required),
      t14.read(u,null.asInstanceOf[T14],required),
      t15.read(u,null.asInstanceOf[T15],required))
  }
}
class Tuple16Template[T1,T2,T3,T4,T5,T6,T7,T8,T9,T10,T11,T12,T13,T14,T15,T16]( t1 : Template[T1],t2 : Template[T2],t3 : Template[T3],t4 : Template[T4],t5 : Template[T5],t6 : Template[T6],t7 : Template[T7],t8 : Template[T8],t9 : Template[T9],t10 : Template[T10],t11 : Template[T11],t12 : Template[T12],t13 : Template[T13],t14 : Template[T14],t15 : Template[T15],t16 : Template[T16]) extends TupleTemplate[Tuple16[T1,T2,T3,T4,T5,T6,T7,T8,T9,T10,T11,T12,T13,T14,T15,T16]]( t1,t2,t3,t4,t5,t6,t7,t8,t9,t10,t11,t12,t13,t14,t15,t16){
  def readTuple(u: Unpacker, required: Boolean) = {
    (t1.read(u,null.asInstanceOf[T1],required),
      t2.read(u,null.asInstanceOf[T2],required),
      t3.read(u,null.asInstanceOf[T3],required),
      t4.read(u,null.asInstanceOf[T4],required),
      t5.read(u,null.asInstanceOf[T5],required),
      t6.read(u,null.asInstanceOf[T6],required),
      t7.read(u,null.asInstanceOf[T7],required),
      t8.read(u,null.asInstanceOf[T8],required),
      t9.read(u,null.asInstanceOf[T9],required),
      t10.read(u,null.asInstanceOf[T10],required),
      t11.read(u,null.asInstanceOf[T11],required),
      t12.read(u,null.asInstanceOf[T12],required),
      t13.read(u,null.asInstanceOf[T13],required),
      t14.read(u,null.asInstanceOf[T14],required),
      t15.read(u,null.asInstanceOf[T15],required),
      t16.read(u,null.asInstanceOf[T16],required))
  }
}
class Tuple17Template[T1,T2,T3,T4,T5,T6,T7,T8,T9,T10,T11,T12,T13,T14,T15,T16,T17]( t1 : Template[T1],t2 : Template[T2],t3 : Template[T3],t4 : Template[T4],t5 : Template[T5],t6 : Template[T6],t7 : Template[T7],t8 : Template[T8],t9 : Template[T9],t10 : Template[T10],t11 : Template[T11],t12 : Template[T12],t13 : Template[T13],t14 : Template[T14],t15 : Template[T15],t16 : Template[T16],t17 : Template[T17]) extends TupleTemplate[Tuple17[T1,T2,T3,T4,T5,T6,T7,T8,T9,T10,T11,T12,T13,T14,T15,T16,T17]]( t1,t2,t3,t4,t5,t6,t7,t8,t9,t10,t11,t12,t13,t14,t15,t16,t17){
  def readTuple(u: Unpacker, required: Boolean) = {
    (t1.read(u,null.asInstanceOf[T1],required),
      t2.read(u,null.asInstanceOf[T2],required),
      t3.read(u,null.asInstanceOf[T3],required),
      t4.read(u,null.asInstanceOf[T4],required),
      t5.read(u,null.asInstanceOf[T5],required),
      t6.read(u,null.asInstanceOf[T6],required),
      t7.read(u,null.asInstanceOf[T7],required),
      t8.read(u,null.asInstanceOf[T8],required),
      t9.read(u,null.asInstanceOf[T9],required),
      t10.read(u,null.asInstanceOf[T10],required),
      t11.read(u,null.asInstanceOf[T11],required),
      t12.read(u,null.asInstanceOf[T12],required),
      t13.read(u,null.asInstanceOf[T13],required),
      t14.read(u,null.asInstanceOf[T14],required),
      t15.read(u,null.asInstanceOf[T15],required),
      t16.read(u,null.asInstanceOf[T16],required),
      t17.read(u,null.asInstanceOf[T17],required))
  }
}
class Tuple18Template[T1,T2,T3,T4,T5,T6,T7,T8,T9,T10,T11,T12,T13,T14,T15,T16,T17,T18]( t1 : Template[T1],t2 : Template[T2],t3 : Template[T3],t4 : Template[T4],t5 : Template[T5],t6 : Template[T6],t7 : Template[T7],t8 : Template[T8],t9 : Template[T9],t10 : Template[T10],t11 : Template[T11],t12 : Template[T12],t13 : Template[T13],t14 : Template[T14],t15 : Template[T15],t16 : Template[T16],t17 : Template[T17],t18 : Template[T18]) extends TupleTemplate[Tuple18[T1,T2,T3,T4,T5,T6,T7,T8,T9,T10,T11,T12,T13,T14,T15,T16,T17,T18]]( t1,t2,t3,t4,t5,t6,t7,t8,t9,t10,t11,t12,t13,t14,t15,t16,t17,t18){
  def readTuple(u: Unpacker, required: Boolean) = {
    (t1.read(u,null.asInstanceOf[T1],required),
      t2.read(u,null.asInstanceOf[T2],required),
      t3.read(u,null.asInstanceOf[T3],required),
      t4.read(u,null.asInstanceOf[T4],required),
      t5.read(u,null.asInstanceOf[T5],required),
      t6.read(u,null.asInstanceOf[T6],required),
      t7.read(u,null.asInstanceOf[T7],required),
      t8.read(u,null.asInstanceOf[T8],required),
      t9.read(u,null.asInstanceOf[T9],required),
      t10.read(u,null.asInstanceOf[T10],required),
      t11.read(u,null.asInstanceOf[T11],required),
      t12.read(u,null.asInstanceOf[T12],required),
      t13.read(u,null.asInstanceOf[T13],required),
      t14.read(u,null.asInstanceOf[T14],required),
      t15.read(u,null.asInstanceOf[T15],required),
      t16.read(u,null.asInstanceOf[T16],required),
      t17.read(u,null.asInstanceOf[T17],required),
      t18.read(u,null.asInstanceOf[T18],required))
  }
}
class Tuple19Template[T1,T2,T3,T4,T5,T6,T7,T8,T9,T10,T11,T12,T13,T14,T15,T16,T17,T18,T19]( t1 : Template[T1],t2 : Template[T2],t3 : Template[T3],t4 : Template[T4],t5 : Template[T5],t6 : Template[T6],t7 : Template[T7],t8 : Template[T8],t9 : Template[T9],t10 : Template[T10],t11 : Template[T11],t12 : Template[T12],t13 : Template[T13],t14 : Template[T14],t15 : Template[T15],t16 : Template[T16],t17 : Template[T17],t18 : Template[T18],t19 : Template[T19]) extends TupleTemplate[Tuple19[T1,T2,T3,T4,T5,T6,T7,T8,T9,T10,T11,T12,T13,T14,T15,T16,T17,T18,T19]]( t1,t2,t3,t4,t5,t6,t7,t8,t9,t10,t11,t12,t13,t14,t15,t16,t17,t18,t19){
  def readTuple(u: Unpacker, required: Boolean) = {
    (t1.read(u,null.asInstanceOf[T1],required),
      t2.read(u,null.asInstanceOf[T2],required),
      t3.read(u,null.asInstanceOf[T3],required),
      t4.read(u,null.asInstanceOf[T4],required),
      t5.read(u,null.asInstanceOf[T5],required),
      t6.read(u,null.asInstanceOf[T6],required),
      t7.read(u,null.asInstanceOf[T7],required),
      t8.read(u,null.asInstanceOf[T8],required),
      t9.read(u,null.asInstanceOf[T9],required),
      t10.read(u,null.asInstanceOf[T10],required),
      t11.read(u,null.asInstanceOf[T11],required),
      t12.read(u,null.asInstanceOf[T12],required),
      t13.read(u,null.asInstanceOf[T13],required),
      t14.read(u,null.asInstanceOf[T14],required),
      t15.read(u,null.asInstanceOf[T15],required),
      t16.read(u,null.asInstanceOf[T16],required),
      t17.read(u,null.asInstanceOf[T17],required),
      t18.read(u,null.asInstanceOf[T18],required),
      t19.read(u,null.asInstanceOf[T19],required))
  }
}
class Tuple20Template[T1,T2,T3,T4,T5,T6,T7,T8,T9,T10,T11,T12,T13,T14,T15,T16,T17,T18,T19,T20]( t1 : Template[T1],t2 : Template[T2],t3 : Template[T3],t4 : Template[T4],t5 : Template[T5],t6 : Template[T6],t7 : Template[T7],t8 : Template[T8],t9 : Template[T9],t10 : Template[T10],t11 : Template[T11],t12 : Template[T12],t13 : Template[T13],t14 : Template[T14],t15 : Template[T15],t16 : Template[T16],t17 : Template[T17],t18 : Template[T18],t19 : Template[T19],t20 : Template[T20]) extends TupleTemplate[Tuple20[T1,T2,T3,T4,T5,T6,T7,T8,T9,T10,T11,T12,T13,T14,T15,T16,T17,T18,T19,T20]]( t1,t2,t3,t4,t5,t6,t7,t8,t9,t10,t11,t12,t13,t14,t15,t16,t17,t18,t19,t20){
  def readTuple(u: Unpacker, required: Boolean) = {
    (t1.read(u,null.asInstanceOf[T1],required),
      t2.read(u,null.asInstanceOf[T2],required),
      t3.read(u,null.asInstanceOf[T3],required),
      t4.read(u,null.asInstanceOf[T4],required),
      t5.read(u,null.asInstanceOf[T5],required),
      t6.read(u,null.asInstanceOf[T6],required),
      t7.read(u,null.asInstanceOf[T7],required),
      t8.read(u,null.asInstanceOf[T8],required),
      t9.read(u,null.asInstanceOf[T9],required),
      t10.read(u,null.asInstanceOf[T10],required),
      t11.read(u,null.asInstanceOf[T11],required),
      t12.read(u,null.asInstanceOf[T12],required),
      t13.read(u,null.asInstanceOf[T13],required),
      t14.read(u,null.asInstanceOf[T14],required),
      t15.read(u,null.asInstanceOf[T15],required),
      t16.read(u,null.asInstanceOf[T16],required),
      t17.read(u,null.asInstanceOf[T17],required),
      t18.read(u,null.asInstanceOf[T18],required),
      t19.read(u,null.asInstanceOf[T19],required),
      t20.read(u,null.asInstanceOf[T20],required))
  }
}
class Tuple21Template[T1,T2,T3,T4,T5,T6,T7,T8,T9,T10,T11,T12,T13,T14,T15,T16,T17,T18,T19,T20,T21]( t1 : Template[T1],t2 : Template[T2],t3 : Template[T3],t4 : Template[T4],t5 : Template[T5],t6 : Template[T6],t7 : Template[T7],t8 : Template[T8],t9 : Template[T9],t10 : Template[T10],t11 : Template[T11],t12 : Template[T12],t13 : Template[T13],t14 : Template[T14],t15 : Template[T15],t16 : Template[T16],t17 : Template[T17],t18 : Template[T18],t19 : Template[T19],t20 : Template[T20],t21 : Template[T21]) extends TupleTemplate[Tuple21[T1,T2,T3,T4,T5,T6,T7,T8,T9,T10,T11,T12,T13,T14,T15,T16,T17,T18,T19,T20,T21]]( t1,t2,t3,t4,t5,t6,t7,t8,t9,t10,t11,t12,t13,t14,t15,t16,t17,t18,t19,t20,t21){
  def readTuple(u: Unpacker, required: Boolean) = {
    (t1.read(u,null.asInstanceOf[T1],required),
      t2.read(u,null.asInstanceOf[T2],required),
      t3.read(u,null.asInstanceOf[T3],required),
      t4.read(u,null.asInstanceOf[T4],required),
      t5.read(u,null.asInstanceOf[T5],required),
      t6.read(u,null.asInstanceOf[T6],required),
      t7.read(u,null.asInstanceOf[T7],required),
      t8.read(u,null.asInstanceOf[T8],required),
      t9.read(u,null.asInstanceOf[T9],required),
      t10.read(u,null.asInstanceOf[T10],required),
      t11.read(u,null.asInstanceOf[T11],required),
      t12.read(u,null.asInstanceOf[T12],required),
      t13.read(u,null.asInstanceOf[T13],required),
      t14.read(u,null.asInstanceOf[T14],required),
      t15.read(u,null.asInstanceOf[T15],required),
      t16.read(u,null.asInstanceOf[T16],required),
      t17.read(u,null.asInstanceOf[T17],required),
      t18.read(u,null.asInstanceOf[T18],required),
      t19.read(u,null.asInstanceOf[T19],required),
      t20.read(u,null.asInstanceOf[T20],required),
      t21.read(u,null.asInstanceOf[T21],required))
  }
}
class Tuple22Template[T1,T2,T3,T4,T5,T6,T7,T8,T9,T10,T11,T12,T13,T14,T15,T16,T17,T18,T19,T20,T21,T22]( t1 : Template[T1],t2 : Template[T2],t3 : Template[T3],t4 : Template[T4],t5 : Template[T5],t6 : Template[T6],t7 : Template[T7],t8 : Template[T8],t9 : Template[T9],t10 : Template[T10],t11 : Template[T11],t12 : Template[T12],t13 : Template[T13],t14 : Template[T14],t15 : Template[T15],t16 : Template[T16],t17 : Template[T17],t18 : Template[T18],t19 : Template[T19],t20 : Template[T20],t21 : Template[T21],t22 : Template[T22]) extends TupleTemplate[Tuple22[T1,T2,T3,T4,T5,T6,T7,T8,T9,T10,T11,T12,T13,T14,T15,T16,T17,T18,T19,T20,T21,T22]]( t1,t2,t3,t4,t5,t6,t7,t8,t9,t10,t11,t12,t13,t14,t15,t16,t17,t18,t19,t20,t21,t22){
  def readTuple(u: Unpacker, required: Boolean) = {
    (t1.read(u,null.asInstanceOf[T1],required),
      t2.read(u,null.asInstanceOf[T2],required),
      t3.read(u,null.asInstanceOf[T3],required),
      t4.read(u,null.asInstanceOf[T4],required),
      t5.read(u,null.asInstanceOf[T5],required),
      t6.read(u,null.asInstanceOf[T6],required),
      t7.read(u,null.asInstanceOf[T7],required),
      t8.read(u,null.asInstanceOf[T8],required),
      t9.read(u,null.asInstanceOf[T9],required),
      t10.read(u,null.asInstanceOf[T10],required),
      t11.read(u,null.asInstanceOf[T11],required),
      t12.read(u,null.asInstanceOf[T12],required),
      t13.read(u,null.asInstanceOf[T13],required),
      t14.read(u,null.asInstanceOf[T14],required),
      t15.read(u,null.asInstanceOf[T15],required),
      t16.read(u,null.asInstanceOf[T16],required),
      t17.read(u,null.asInstanceOf[T17],required),
      t18.read(u,null.asInstanceOf[T18],required),
      t19.read(u,null.asInstanceOf[T19],required),
      t20.read(u,null.asInstanceOf[T20],required),
      t21.read(u,null.asInstanceOf[T21],required),
      t22.read(u,null.asInstanceOf[T22],required))
  }
}