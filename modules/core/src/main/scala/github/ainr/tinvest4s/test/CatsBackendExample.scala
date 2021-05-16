package github.ainr.tinvest4s.test

import cats.Monad
import cats.effect.{Concurrent, ContextShift, ExitCode, IO, IOApp, Resource, Sync}
import github.ainr.tinvest4s.config.access.InvestAccessConfig
import github.ainr.tinvest4s.domain.schemas.Operation
import github.ainr.tinvest4s.http.client.InvestApiClient
import github.ainr.tinvest4s.http.client.interpreters.InvestApiSttpClient
import github.ainr.tinvest4s.http.client.interpreters.InvestApiSttpClient.InvestApiResponseError
import sttp.client3.asynchttpclient.cats.AsyncHttpClientCatsBackend

object CatsBackendExample extends IOApp {

  override def run(args: List[String]): IO[ExitCode] = {
    createClient[IO]()
      .use { investApi => for {
        portfolio         <- investApi.portfolio()
        _                 <- IO.delay(println(portfolio))
        limitOrderResult  <- investApi.limitOrder("BBG005HLSZ23", 1, Operation.Buy(), 10)
        _                 <- IO.delay(println(limitOrderResult))
        marketOrderResult <- investApi.marketOrder("BBG005HLSZ23", 1, Operation.Buy())
        _                 <- IO.delay(println(marketOrderResult))
      } yield ExitCode.Success
    }
  }

  def createClient[F[_]: Sync: Monad: Concurrent: ContextShift](): Resource[F, InvestApiClient[F]] = {
    val config = InvestAccessConfig(token = "t.kkxK5DlAIBodw5moQBIDF1zKSMD-Ov4Kfr5hrBSrTRaxOcRTaeSVKYIdiZXsbSuakLyq9fUK0NUe672oItp6xA")
    def errorHandler(error: InvestApiResponseError): Unit = {
      println(error)
    }
    AsyncHttpClientCatsBackend.resource().map {
      backend => new InvestApiSttpClient[F](config, backend)(errorHandler)
    }
  }
}