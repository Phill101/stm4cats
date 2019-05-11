import xerial.sbt.Sonatype._
import sbtcrossproject.CrossPlugin.autoImport.{crossProject, CrossType}

inThisBuild(Seq(
  organization := "com.olegpy",
  scalaVersion := "2.12.8",
  version := "0.1.0-SNAPSHOT",
  crossScalaVersions := Seq("2.11.12", "2.12.8"),
  pgpPassphrase := sys.env.get("PGP_PASS").map(_.toArray),
  licenses += ("MIT", url("http://opensource.org/licenses/MIT"))
))

lazy val root = project.in(file("."))
  .aggregate(stm4cats.js, stm4cats.jvm)
  .settings(
    name := "stm4cats",
    publish := {},
    publishLocal := {},
    publishArtifact := false,
    publishTo := sonatypePublishTo.value,
  )

lazy val stm4cats = crossProject(JSPlatform, JVMPlatform)
  .crossType(CrossType.Full)
  .in(file("."))
  .settings(
    name := "stm4cats",
    fork in test := true,
    libraryDependencies ++= Seq(
      "org.typelevel" %%% "cats-effect" % "1.2.0",
      "com.lihaoyi" %%% "utest" % "0.6.7" % Test,
      "org.typelevel" %%% "cats-laws" % "1.5.0" % Test,
      "org.typelevel" %%% "cats-effect-laws" % "1.2.0" % Test,
    ),

    testFrameworks += new TestFramework("utest.runner.Framework"),

    scalacOptions --= Seq(
      "-Xfatal-warnings",
      "-Ywarn-unused:params",
      "-Ywarn-unused:implicits",
    ),
    publishTo := sonatypePublishTo.value,
    publishMavenStyle := true,
    sonatypeProjectHosting :=
      Some(GitHubHosting("oleg-py", "stm4cats", "oleg.pyzhcov@gmail.com")),
  )
