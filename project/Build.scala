import sbt._
import Keys._
import Process._
import xml.XML

object MessagePackScalaBuild extends Build {
  
  val messagePackVersion = "0.6.6-SNAPSHOT"


  override lazy val settings = super.settings ++
      Seq(
        organization := "org.msgpack",
        name := "msgpack-scala",
        version := messagePackVersion,
        scalaVersion := "2.9.1",
        crossScalaVersions := Seq("2.9.0","2.9.0-1","2.9.1","2.9.1-1"),
        resolvers ++= Seq(Resolver.mavenLocal),
        parallelExecution in Test := false
      )
  
  lazy val dependencies = Seq(
    "org.msgpack" % "msgpack" % messagePackVersion,
    "org.slf4j" % "slf4j-api" % "1.6.4" % "provided"
  )
  
  lazy val dependenciesForTest = Seq(
    "junit" % "junit" % "4.8.1" % "test",
    "log4j" % "log4j" % "1.2.16" % "test"
  )

  lazy val dependsOnScalaVersion = (scalaVersion) { v => {
    val specs = v match{
      case "2.9.1-1" => "org.scala-tools.testing" % "specs_2.9.1" % "1.6.9" % "test"
      case "2.9.1"  => "org.scala-tools.testing" %% "specs" % "1.6.9" % "test"
      case _ => "org.scala-tools.testing" %% "specs" % "1.6.8" % "test"
    }
    Seq(
      "org.scala-lang" % "scalap" % v,
      specs
    )
  }}
  
  

  lazy val root = Project(id = "msgpack-scala",
                          base = file("."),
                          settings = Project.defaultSettings ++ Seq(
                            libraryDependencies ++= dependencies,
                            libraryDependencies ++= dependenciesForTest,
                            libraryDependencies <++= dependsOnScalaVersion,
                            pomExtra := loadPomExtra()
                            )
  )

  def loadPomExtra() = {
    XML.loadFile( file( "./project/pomExtra.xml")).child
  }
}