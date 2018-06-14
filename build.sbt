import ReleaseTransformations._

val SCALA_2_11 = "2.11.12"
val SCALA_2_12 = "2.12.6"
val SCALA_2_13 = "2.13.0-M4"

val buildSettings = Seq[Setting[_]](
  organization := "org.msgpack",
  organizationName := "MessagePack",
  organizationHomepage := Some(new URL("http://msgpack.org/")),
  description := "MessagePack for Scala",
  scalaVersion := SCALA_2_12,
  crossScalaVersions := Seq(SCALA_2_13, SCALA_2_12, SCALA_2_11),
  logBuffered in Test := false,
  licenses += ("Apache-2.0", url("https://www.apache.org/licenses/LICENSE-2.0.html")),
  homepage := Some(url("https://github.com/msgpack/msgpack-scala")),
  scmInfo := Some(
    ScmInfo(
      browseUrl = url("https://github.com/msgpack/msgpack-scala"),
      connection = "scm:git@github.com:msgpack/msgpack-scala.git"
    )
  ),
  developers := List(
    Developer(id = "xerial", name = "Taro L. Saito", email = "leo@xerial.org", url = url("http://xerial.org/leo")),
    Developer(id = "takezoux2", name = "Yositeru Takeshita", email = "takezoux2@gmail.com", url = url("https://github.com/takezoux2"))
  ),
  // Use sonatype resolvers
  resolvers ++= Seq(
    Resolver.sonatypeRepo("releases"),
    Resolver.sonatypeRepo("snapshots")
  ),
  // JVM options for building
  scalacOptions ++= Seq("-encoding", "UTF-8", "-deprecation", "-unchecked", "-feature"),
  // Release settings
  publishTo := Some(
    if (isSnapshot.value) {
      Opts.resolver.sonatypeSnapshots
    } else {
      Opts.resolver.sonatypeStaging
    }
  ),
  releaseCrossBuild := true,
  releaseTagName := { (version in ThisBuild).value },
  releaseProcess := Seq[ReleaseStep](
    checkSnapshotDependencies,
    inquireVersions,
    runClean,
    runTest,
    setReleaseVersion,
    commitReleaseVersion,
    tagRelease,
    releaseStepCommand("publishSigned"),
    setNextVersion,
    commitNextVersion,
    releaseStepCommand("sonatypeReleaseAll"),
    pushChanges
  )
)

// Project settings
lazy val root = Project(id = "msgpack-scala-root", base = file("."))
  .settings(
    buildSettings,
    // Do not publish the root project
    publishArtifact := false,
    publish := {},
    publishLocal := {}
  ).aggregate(msgpackScala)

lazy val msgpackScala = Project(id = "msgpack-scala", base = file("msgpack-scala"))
  .settings(
    buildSettings,
    description := "MesasgePack for Scala",
    libraryDependencies ++= Seq(
      "org.msgpack"    % "msgpack-core" % "0.8.13",
      "org.scalatest"  %% "scalatest"   % "3.0.4" % "test",
      "org.scalacheck" %% "scalacheck"  % "1.13.5" % "test"
    )
  )
