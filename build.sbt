import de.johoop.jacoco4sbt._

name := "complate-spring-mvc"
organization := "com.github.complate"
description := "Integration of the Complate template library into Spring MVC"

version := "0.3.0-SNAPSHOT"

scalaVersion := "2.12.2"

publishMavenStyle := true
publishArtifact in Test := false
pomIncludeRepository := (_ => false)
startYear := Some(2017)
organizationHomepage := Some(url("https://github.com/complate"))
homepage := Some(url("https://github.com/complate/complate-spring-mvc"))
developers := List(
  Developer("dwestheide", "Daniel Westheide", "", url("https://github.com/dwestheide")))
licenses := Seq("APL2" -> url("http://www.apache.org/licenses/LICENSE-2.0.txt"))
scmInfo := Some(ScmInfo(
  browseUrl = url("https://github.com/complate/complate-spring-mvc"),
  connection = "scm:git:git@github.com:complate/complate-spring-mvc.git"
))
publishTo := Some(
  if (isSnapshot.value) Opts.resolver.sonatypeSnapshots
  else Opts.resolver.sonatypeStaging
)
crossPaths := false
autoScalaLibrary := false

libraryDependencies ++= Seq(
  "org.springframework" % "spring-webmvc" % "4.3.10.RELEASE",
  "javax.servlet" % "javax.servlet-api" % "3.1.0" % "provided",
  "org.springframework" % "spring-test" % "4.3.10.RELEASE" % "test",
  "org.scalatest" %% "scalatest" % "3.0.3" % "test",
  "org.mockito" % "mockito-core" % "2.8.47" % "test"
)

jacoco.settings

jacoco.reportFormats in jacoco.Config := Seq(
  XMLReport(encoding = "utf-8"),
  ScalaHTMLReport(withBranchCoverage = true))
