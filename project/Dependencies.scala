import sbt._

object Dependencies {

  object V {
    val http4s              = "0.21.7"
    val circe               = "0.13.0"
    val scalatest           = "3.2.0"
    val cats                = "2.1.1"
    val http4sJdkHttpClient = "0.3.1"
  }

  object Libraries {

    val scalatest           = "org.scalatest" %% "scalatest" % V.scalatest

    val catsCore            = "org.typelevel" %% "cats-core" % V.cats
    val catsEffect          = "org.typelevel" %% "cats-effect" % V.cats

    val circeCore           = "io.circe" %% "circe-core" % V.circe
    val circeParser         = "io.circe" %% "circe-parser" % V.circe
    val circeGeneric        = "io.circe" %% "circe-generic" % V.circe
    val circeLiteral        = "io.circe" %% "circe-literal" % V.circe
    val circeGenericExtras  = "io.circe" %% "circe-generic-extras" % V.circe

    val http4sDsl           = "org.http4s" %% "http4s-dsl" % V.http4s
    val http4sCirce         = "org.http4s" %% "http4s-circe" % V.http4s
    val http4sBlazeClient   = "org.http4s" %% "http4s-blaze-client" % V.http4s
    val http4sJdkHttpClient = "org.http4s" %% "http4s-jdk-http-client" % V.http4sJdkHttpClient
  }

  object CompilerPlugin {

  }
}
