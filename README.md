# MessagePack for Scala

An implementation of [MessagePack](http://msgpack.org/) for Scala.

## Quick Start

Quick Start is available at [MessagePack official site](http://wiki.msgpack.org/display/MSGPACK/QuickStart+for+Scala).

## Requirement

* sbt 0.12.2 or later


## Installation


### By using sbt

This project also supports sbt 0.13.5
To build JAR file of MessagePack for Scala, please type the following command:

    $ package

## How to release the project

To relese the project (compile, test, tagging, deploy), please use the commands as follows:

    $ mvn release:prepare
    $ mvn release:perform


## Make intellij project

Please type the following command.Then intellij project will be generated.

    $ sbt gen-idea
    


