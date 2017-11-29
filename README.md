# MessagePack for Scala
[![Build Status](https://travis-ci.org/msgpack/msgpack-scala.svg?branch=master)](https://travis-ci.org/msgpack/msgpack-scala)

- Message Pack specification: https://github.com/msgpack/msgpack/blob/master/spec.md

## Quick Start

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/org.msgpack/msgpack-scala/badge.svg)](https://maven-badges.herokuapp.com/maven-central/org.msgpack/msgpack-scala/)
[![Javadoc](https://javadoc-emblem.rhcloud.com/doc/org.msgpack/msgpack-scala/badge.svg)](http://www.javadoc.io/doc/org.msgpack/msgpack-scala)

```
libraryDependencies += "org.msgpack" %% "msgpack-scala" % "(version)"
```


## For MessagePack Developers

### Basic sbt commands
Enter the sbt console:
```
$ ./sbt
```

Here is a list of sbt commands for daily development:
```
> ~compile                                 # Compile source codes
> ~test:compile                            # Compile both source and test codes
> ~test                                    # Run tests upon source code change
> ~test-only *MessagePackTest              # Run tests in the specified class
> ~test-only *MessagePackTest -- -n prim   # Run the test tagged as "prim"
> project msgpack-scala                    # Focus on a specific project
> package                                  # Create a jar file in the target folder of each project
> scalafmt                                 # Reformat source codes
> ; coverage; test; coverageReport; coverageAggregate;  # Code coverage
```

### Publishing

```
> publishLocal            # Install to local .ivy2 repository
> publish                 # Publishing a snapshot version to the Sonatype repository

> release                 # Run the release procedure (set a new version, run tests, upload artifacts, then deploy to Sonatype)
```

For publishing to Maven central, msgpack-scala uses [sbt-sonatype](https://github.com/xerial/sbt-sonatype) plugin. Set Sonatype account information (user name and password) in the global sbt settings. To protect your password, never include this file in your project.

___$HOME/.sbt/(sbt-version)/sonatype.sbt___

```
credentials += Credentials("Sonatype Nexus Repository Manager",
        "oss.sonatype.org",
        "(Sonatype user name)",
        "(Sonatype password)")
```
