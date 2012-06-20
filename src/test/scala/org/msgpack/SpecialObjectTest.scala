package org.msgpack

import annotation.Message
import org.specs.Specification
import org.specs.runner.{JUnitSuiteRunner, JUnit}
import org.junit.runner.RunWith

/**
 * Test special class for Scala
 * User: takeshita
 * Create: 12/06/20 23:58
 */

@RunWith(classOf[JUnitSuiteRunner])
class SpecialObjectTest  extends Specification with JUnit {
  import specialobj._

  "Option" should{
    "ser/des Some" in{
      val b = ScalaMessagePack.write(Some("aaa"))
      //ScalaMessagePack.read[Some[String]] must_== Some("aaa") this doesn't work because type info of String is erased at runtime.
      ScalaMessagePack.read[OptionLike](b) must_== OptionLike(true,"aaa")
    }
    "ser/des None" in{
      val b = ScalaMessagePack.write(None)
      ScalaMessagePack.read[Option[String]](b) must_== None
      ScalaMessagePack.read[OptionLike](b) must_== OptionLike(false,null)
    }
  }
  "Either" should{
    "ser/des Left" in{
      val b = ScalaMessagePack.write(Left("aaa"))
      ScalaMessagePack.read[EitherLike](b) must_== EitherLike(false,"aaa",null)

    }
    "ser/des Right" in{
      val b = ScalaMessagePack.write(Right("bbb"))
      ScalaMessagePack.read[EitherLike](b) must_== EitherLike(true,null,"bbb")

    }
  }

  import ScalaMessagePack._
  "scala's Enum" should{
    "ser/des exist id" in{

      read[EnumContainerClass](ScalaMessagePack.write(EnumContainerClass(ScalaEnum.Zero))) must_== EnumContainerClass(ScalaEnum.Zero)
      read[EnumContainerClass](ScalaMessagePack.write(EnumContainerClass(ScalaEnum.One))) must_== EnumContainerClass(ScalaEnum.One)
      read[EnumContainerClass](ScalaMessagePack.write(EnumContainerClass(ScalaEnum.Two))) must_== EnumContainerClass(ScalaEnum.Two)
    }
    "ser/des from int" in{
      read[EnumContainerClass](ScalaMessagePack.write(EnumContainerDummy(0))) must_== EnumContainerClass(ScalaEnum.Zero)
      read[EnumContainerClass](ScalaMessagePack.write(EnumContainerDummy(1))) must_== EnumContainerClass(ScalaEnum.One)
      read[EnumContainerClass](ScalaMessagePack.write(EnumContainerDummy(2))) must_== EnumContainerClass(ScalaEnum.Two)
    }
    "des not exist id" in{
      read[EnumContainerClass](ScalaMessagePack.write(EnumContainerDummy(-1))) must_== EnumContainerClass(null)
      read[EnumContainerClass](ScalaMessagePack.write(EnumContainerDummy(3))) must_== EnumContainerClass(null)
      read[EnumContainerClass](ScalaMessagePack.write(EnumContainerDummy(324354))) must_== EnumContainerClass(null)
    }
  }

}
package specialobj{

  @Message
  case class OptionLike(var some_? : Boolean,var  value : String){
    def this() = this(false,null)
  }
  @Message
  case class EitherLike(var right_? : Boolean ,var left : String, var right : String){
    def this() = this(false,null,null)
  }

  @Message
  case class EnumContainerClass(var enum : ScalaEnum.Value){
    def this() = this(ScalaEnum.Zero)

  }

  @Message
  case class EnumContainerDummy(var enumV : Int){
    def this() = this(0)
  }

  object ScalaEnum extends Enumeration{

    val Zero = Value(0,"zero")
    val One = Value(1)
    val Two = Value(2)

  }


}

