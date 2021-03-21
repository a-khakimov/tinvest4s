package github.ainr.tinvest4s.test

import github.ainr.tinvest4s.config.access.InvestAccessConfig
import github.ainr.tinvest4s.http.client.interpreters.InvestApiSttpClient
import github.ainr.tinvest4s.http.error.DefaultErrorHandler
import sttp.client3.circe.asJson
import sttp.client3.httpclient.zio.{HttpClientZioBackend, SttpClient, send}
import sttp.client3.{UriContext, basicRequest}
import zio._
import zio.console.Console


object TestApp extends zio.App {

  override def run(args: List[String]): URIO[zio.ZEnv, ExitCode] = {

    HttpClientZioBackend().flatMap {
      backend => {

        val config = InvestAccessConfig(token = "t.kkxK5DlAIBodw5moQBIDF1zKSMD-Ov4Kfr5hrBSrTRaxOcRTaeSVKYIdiZXsbSuakLyq9fUK0NUe672oItp6xA")
        val errorHandler = new DefaultErrorHandler()
        val client = new InvestApiSttpClient(config, backend, errorHandler)

        val sendWithRetries: ZIO[Console with SttpClient, Throwable, Unit] = for {
          _ <- console.putStrLn("hui")
          p = client.portfolio()
          _ <- console.putStrLn(p.toString)
        } yield ()

        sendWithRetries
      }
    }.exitCode
  }
}


object GetAndParseJsonZioCirce extends App {


  import io.circe.generic.auto._

  override def run(args: List[String]): ZIO[ZEnv, Nothing, ExitCode] = {

    case class HttpBinResponse(origin: String, headers: Map[String, String], i: Int)

    val request = basicRequest
      .get(uri"https://httpbin.org/get")
      .response(asJson[HttpBinResponse])

    // create a description of a program, which requires two dependencies in the environment:
    // the SttpClient, and the Console
    val sendAndPrint: ZIO[Console with SttpClient, Throwable, Unit] = for {
      response <- send(request)
      _ <- console.putStrLn(s"Got response code: ${response.code}")
      _ <- console.putStrLn(response.body.toString)
    } yield ()

    // provide an implementation for the SttpClient dependency; other dependencies are
    // provided by Zio
    sendAndPrint
      .provideCustomLayer(HttpClientZioBackend.layer())
      .exitCode
  }
}
