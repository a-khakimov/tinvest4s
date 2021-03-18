import Dependencies._

ThisBuild / scalaVersion := "2.13.4"
ThisBuild / version := "0.1"
ThisBuild / organization := "dev.github.ainr"
ThisBuild / organizationName := "ainr"
ThisBuild / name := "tinvest4s"

lazy val root = (project in file("."))
  .settings(
    name := "tinvest4s"
  )
  .aggregate(core, tests)

lazy val tests = (project in file("modules/tests"))
  .settings(
    name := "tinvest4s-test-suite",
    libraryDependencies ++= Seq(
      Libraries.scalatest,
      Libraries.scalamock
    )
  )
  .dependsOn(core)

lazy val core = (project in file("modules/core"))
  .settings(
    name := "tinvest4s",
    sonarProperties := Sonar.properties,
    scalacOptions ++= Seq(
      "-Xfatal-warnings",
      "-deprecation"
    ),
    libraryDependencies ++= Seq(
      Libraries.catsCore,
      Libraries.catsEffect,
      Libraries.circeCore,
      Libraries.circeGeneric,
      Libraries.circeGenericExtras,
      Libraries.circeLiteral,
      Libraries.circeParser,
      Libraries.http4sBlazeClient,
      Libraries.http4sCirce,
      Libraries.http4sDsl,
      Libraries.http4sJdkHttpClient,
      Libraries.newtype
    )
  )
