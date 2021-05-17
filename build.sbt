import Dependencies.{Libraries, _}

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
      Libraries.circeCore,
      Libraries.circeGeneric,
      Libraries.circeGenericExtras,
      Libraries.circeLiteral,
      Libraries.circeParser,
      Libraries.newtype,
      Libraries.`sttp-backend-zio`,
      Libraries.`sttp-client-core`,
      Libraries.`sttp-client3-circe`,
      Libraries.catsCore,
      Libraries.zio_interop_cats,
      Libraries.cats_effect,
      Libraries.`async-http-client-backend-cats-ce2`
    )
  )
