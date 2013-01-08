import sbt._
import Keys._
import Process._
import xml.XML

object MessagePackScalaBuild extends Build {
  
  val messagePackVersion = "0.6.8-SNAPSHOT"


  override lazy val settings = super.settings ++
      Seq(
        organization := "org.msgpack",
        name := "msgpack-scala",
        version := messagePackVersion,
        scalaVersion := "2.9.2",
        crossScalaVersions := Seq("2.9.0-1","2.9.1","2.9.1-1","2.9.2"),
        resolvers ++= Seq(Resolver.mavenLocal),
        parallelExecution in Test := false
      )
  
  lazy val dependencies = Seq(
    "org.msgpack" % "msgpack" % messagePackVersion,
    "org.slf4j" % "slf4j-api" % "1.6.4" % "provided"
  )
  
  lazy val dependenciesForTest = Seq(
    "junit" % "junit" % "4.8.1" % "test",
    "org.slf4j" % "slf4j-nop" % "1.7.2" % "test"
  )

  lazy val dependsOnScalaVersion = (scalaVersion) { v => {
    val specs = v match{
      case "2.9.2"  => "org.specs2" %% "specs2" % "1.12.3" % "test"
      case "2.9.1-1" => "org.specs2" %% "specs2" % "1.12.3" % "test"
      case "2.9.1"  => "org.specs2" %% "specs2" % "1.12.3" % "test"
      case "2.9.0-1"  => "org.specs2" %% "specs2" % "1.8.2" % "test"
      case "2.9.0"  => "org.specs2" %% "specs2" % "1.7.1" % "test"
      case _ => "org.specs2" %% "specs2" % "1.8.2" % "test"
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
                            publishMavenStyle := true,
                            publishArtifact in Test := false,
                            publishTo <<= version { (v: String) =>
                              val nexus = "https://oss.sonatype.org/"
                              if (v.trim.endsWith("SNAPSHOT")) 
                                Some("snapshots" at nexus + "content/repositories/snapshots") 
                              else
                                Some("releases"  at nexus + "service/local/staging/deploy/maven2")
                            },
                            pomIncludeRepository := { _ => false },
                            pomExtra := loadPomExtra()
                            )
  )

  def loadPomExtra() = {
    XML.loadFile( file( "./project/pomExtra.xml")).child
  }
}
