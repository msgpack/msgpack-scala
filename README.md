# MessagePack for Scala

An implementation of [MessagePack](http://msgpack.org/) for Scala.

## Quick Start

Quick Start is available at [MessagePack official site](http://wiki.msgpack.org/display/MSGPACK/QuickStart+for+Scala).

## Requirement

* Scala 2.9.x
* maven 2 or later
* (Additional) sbt 0.11.2

If you want to compile in scala version 2.9.1 or 2.9.0-1, replace pom.xml property or use sbt.

## Installation

### By using maven

To build the JAR file of MessagePack for Scala, you can use Maven (http://maven.apache.org), then type the following command:

    $ mvn package

To locally install the project, type

    $ mvn install

Next, open the preference page in Eclipse and add the CLASSPATH variable:

    M2_REPO = $HOME/.m2/repository

where $HOME is your home directory. In Windows XP, $HOME is:

    C:/Documents and Settings/(user name)/.m2/repository

### By using sbt

This project also supports sbt 0.11.2.
To build JAR file of MessagePack for Scala, please type the following command:

    $ package

To generate pom files, plese type the following command:

    $+ make-pom

As a result, the pom files are generated into target/{scala.version}.

## How to release the project

To relese the project (compile, test, tagging, deploy), please use the commands as follows:

    $ mvn release:prepare
    $ mvn release:perform


