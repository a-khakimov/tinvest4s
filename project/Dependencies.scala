import sbt._

object Dependencies {

  object V {
    val http4s              = "0.21.7"
    val circe               = "0.13.0"
    val cats                = "2.1.1"
    val http4sJdkHttpClient = "0.3.1"
    val newtype             = "0.4.3"
    val scalatest           = "3.2.0"
    val scalamock           = "5.1.0"
    val sttp                = "3.3.3"
    val zio_interop_cats    = "3.0.2.0"
    val `async-http-client-backend-cats-ce2` = "3.3.3"
  }

  object Libraries {

    val `sttp-client-core` = "com.softwaremill.sttp.client3" %% "core" % V.sttp
    val `sttp-backend-zio` = "com.softwaremill.sttp.client3" %% "httpclient-backend-zio" % V.sttp
    val `sttp-client3-circe` = "com.softwaremill.sttp.client3" %% "circe" % V.sttp
    val `async-http-client-backend-cats-ce2` = "com.softwaremill.sttp.client3" %% "async-http-client-backend-cats-ce2" % V.`async-http-client-backend-cats-ce2` // for cats-effect 2.x

    val newtype             = "io.estatico"   %% "newtype" % V.newtype
    val scalatest           = "org.scalatest" %% "scalatest" % V.scalatest
    val scalamock           = "org.scalamock" %% "scalamock" % V.scalamock

    val catsCore            = "org.typelevel" %% "cats-core" % V.cats

    val circeCore           = "io.circe" %% "circe-core" % V.circe
    val circeParser         = "io.circe" %% "circe-parser" % V.circe
    val circeGeneric        = "io.circe" %% "circe-generic" % V.circe
    val circeLiteral        = "io.circe" %% "circe-literal" % V.circe
    val circeGenericExtras  = "io.circe" %% "circe-generic-extras" % V.circe
    val zio_interop_cats    = "dev.zio" %% "zio-interop-cats" % V.zio_interop_cats
    val cats_effect         = "org.typelevel" %% "cats-core" % V.cats
  }

  object CompilerPlugin {

  }
}
