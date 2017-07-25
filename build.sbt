name := "complate-spring-mvc"

organization := "complate"

version := "0.1-SNAPSHOT"

scalaVersion := "2.12.2"

description := "Integration of the Complate template library into Spring MVC"

publishMavenStyle := true

crossPaths := false

autoScalaLibrary := false

libraryDependencies ++= Seq(
  "org.springframework" % "spring-webmvc" % "4.3.10.RELEASE",
  "javax.servlet" % "javax.servlet-api" % "3.1.0" % "provided",
  "org.springframework" % "spring-test" % "4.3.10.RELEASE",
  "org.scalatest" %% "scalatest" % "3.0.3" % "test",
  "org.mockito" % "mockito-core" % "2.8.47" % "test"
)
