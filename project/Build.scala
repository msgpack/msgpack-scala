import sbt._
import Keys._
import Process._
import xml.XML

object MessagePackScalaBuild extends Build {
  
  val messagePackVersion = "0.6.4-SNAPSHOT"


  override lazy val settings = super.settings ++
      Seq(
        organization := "org.msgpack",
        name := "msgpack-scala",
        version := messagePackVersion,
        scalaVersion := "2.9.0",
        crossScalaVersions := Seq("2.9.0","2.9.0-1","2.9.1"),
        resolvers ++= Seq(Resolver.mavenLocal),
        parallelExecution in Test := false
      )
  
  lazy val dependencies = Seq(
    "org.msgpack" % "msgpack" % messagePackVersion
  )
  
  lazy val dependenciesForTest = Seq(
    "junit" % "junit" % "4.8.1" % "test",
    "log4j" % "log4j" % "1.2.16" % "test"
  )

  lazy val scalaSpecs = (scalaVersion) { v => { v match{
      case "2.9.1" => "org.scala-tools.testing" %% "specs" % "1.6.9" % "test"
      case _ => "org.scala-tools.testing" %% "specs" % "1.6.8" % "test"
    }}}
  

  lazy val root = Project(id = "msgpack-scala",
                          base = file("."),
                          settings = Project.defaultSettings ++ Seq(
                            libraryDependencies ++= dependencies,
                            libraryDependencies ++= dependenciesForTest,
                            libraryDependencies <+= scalaSpecs,
                            pomExtra := loadPomExtra()
                            )
  )

  def loadPomExtra() = {
    XML.loadFile( file( "./project/pomExtra.xml")).child
  }
}