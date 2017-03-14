import sbt._
import Keys._
import xml.XML
import org.sbtidea.SbtIdeaPlugin
import xerial.sbt.Sonatype.sonatypeSettings

val messagePackVersion = "0.6.12"

val scala211 = "2.11.8"

lazy val settings =
  Seq(
    organization := "org.msgpack",
    name := "msgpack-scala",
    version := messagePackVersion,
    scalaVersion := scala211,
    crossScalaVersions := Seq("2.10.5", scala211, "2.12.1"), // After 2.10 ,binaries are compatible.So don't need to crossCompile.(tests are passed even comment outed versions.)
    resolvers ++= Seq(Resolver.mavenLocal),
    parallelExecution in Test := false,
    scalacOptions ++= {
      scalaVersion.value match {
        case v if v.startsWith("2.12") => Seq("-target:jvm-1.8")
        case _                         => Seq("-target:jvm-1.7")
      }
    }
  )

lazy val dependencies = Seq(
  "org.msgpack" % "msgpack" % messagePackVersion,
  "org.slf4j" % "slf4j-api" % "1.6.4" % "provided"
)

lazy val dependenciesForTest = Seq(
  "junit" % "junit" % "4.11" % "test",
  "org.slf4j" % "slf4j-nop" % "1.7.2" % "test"
)

lazy val dependsOnScalaVersion = (scalaVersion) { v => {
  val specs = v match{
    case x if x.startsWith("2.10") => "org.specs2" %% "specs2" % "1.14" % "test"
    case x if x.startsWith("2.11") => "org.specs2" %% "specs2" % "2.4.1" % "test"
    case x if x.startsWith("2.12") => "org.specs2" %% "specs2" % "2.4.17" % "test"
    case _ => "org.specs2" %% "specs2" % "1.8.2" % "test"
  }
  val reflection = v match{
    case x if x.startsWith("2.10") => List("org.scala-lang" % "scalap" % v)
    case x if x.startsWith("2.11") => List("org.scala-lang" % "scalap" % v)//List("org.scala-lang" % "scala-reflect" % v)
    case x if x.startsWith("2.12") => List("org.scala-lang" % "scalap" % v)
    case v => List("org.scala-lang" % "scalap" % v)
  }
  specs :: reflection
}}


lazy val root = Project(id = "msgpack-scala",
  base = file("."),
  settings = Defaults.coreDefaultSettings ++ settings ++ SbtIdeaPlugin.settings ++ sonatypeSettings ++ Seq(
    libraryDependencies ++= dependencies,
    libraryDependencies ++= dependenciesForTest,
    libraryDependencies ++= { dependsOnScalaVersion.value },
    publishMavenStyle := true,
    publishArtifact in Test := false,
    /*publishTo <<= version { (v: String) =>
      val nexus = "https://oss.sonatype.org/"
      if (v.trim.endsWith("SNAPSHOT"))
        Some("snapshots" at nexus + "content/repositories/snapshots")
      else
        Some("releases"  at nexus + "service/local/staging/deploy/maven2")
    },*/
    pomIncludeRepository := { _ => false },
    pomExtra := loadPomExtra()
  )
)

def loadPomExtra() = {
  XML.loadFile( file( "./project/pomExtra.xml")).child
}
