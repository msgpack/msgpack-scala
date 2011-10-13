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

import annotation.Message
import org.junit.runner.RunWith
import org.specs.runner.{JUnit, JUnitSuiteRunner}
import org.specs._
import org.specs.matcher._

/**
 * 
 * User: takeshita
 * Create: 11/10/12 23:17
 */

@RunWith(classOf[JUnitSuiteRunner])
class UsageTest extends Specification with JUnit{

  "Normal usage" should{

    "write and read" in{
      val user = new User
      user.id = 439
      user.name = "sizuru"
      user.roles = Array("user")
      user.profile.gender = 2

      val data = ScalaMessagePack.write(user)
      data must notBeNull
      for(b <- data){
        print("%02x ".format(b))
      }
      println("")

      val recover = ScalaMessagePack.read[User](data)

      recover.id must_== user.id
      recover.name must_== user.name
      recover.roles.toList must_== user.roles.toList
      recover.profile.gender must_== user.profile.gender

    }

  }


}
@Message
class User{

  var id : Long = 0
  var name : String = ""
  var roles : Array[String] = Array("admin","user")

  var profile : Profile = new Profile

}

@Message
class Profile{
  var gender : Int = 1
}

