name := "tinest4s-example"

version := "0.1"

scalaVersion := "2.13.4"

lazy val tinvest4s = ProjectRef(uri("https://github.com/a-khakimov/tinvest4s.git#7060579ad4b058fa56041812cb078d4a80b1a8c4"), "tinvest4s")
lazy val root = (project in file(".")).dependsOn(tinvest4s)

