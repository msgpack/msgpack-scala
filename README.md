# MessagePack for Scala

[![Build Status](https://travis-ci.org/msgpack/msgpack-scala.svg?branch=master)](https://travis-ci.org/msgpack/msgpack-scala)

An implementation of [MessagePack](http://msgpack.org/) for Scala.

## Requirement

* sbt 0.13.5 or later


## SBT

Add dependency.

    libraryDependencies += "org.msgpack" %% "msgpack-scala" % "0.6.11"


## For developer

### Make intellij project

Please type the following command.Then intellij project will be generated.

    $ sbt gen-idea
    

# Quick Start


## Serialize/Deserialize class

```scala

import org.msgpack.annotation.Message
import org.msgpack.ScalaMessagePack

@Message // Don't forget to add Message annotation.
class YourClass{
  var name : String = ""
  var age : Int = 2
}

val obj = new YourClass()
obj.name = "hoge"
obj.age = 22
val serialized : Array[Byte] = ScalaMessagePack.write(obj)

val deserialized : YourClass = ScalaMessagePack.read[YourClass](serialized)

```

* Sorry, case classes are not supported now.


