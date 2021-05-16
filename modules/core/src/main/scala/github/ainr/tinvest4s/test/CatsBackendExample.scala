package github.ainr.tinvest4s.test

import cats.Monad
import cats.effect.{Concurrent, ContextShift, ExitCode, IO, IOApp, Resource, Sync}
import github.ainr.tinvest4s.config.access.InvestAccessConfig
import github.ainr.tinvest4s.http.client.InvestApiClient
import github.ainr.tinvest4s.http.client.interpreters.InvestApiSttpClient
import github.ainr.tinvest4s.http.client.interpreters.InvestApiSttpClient.InvestApiResponseError
import sttp.client3.asynchttpclient.cats.AsyncHttpClientCatsBackend

object CatsBackendExample extends IOApp {

  override def run(args: List[String]): IO[ExitCode] = {
    createClient[IO]()
      .use { investApi => for {
        portfolio <- investApi.portfolio()
        _ <- IO.delay(println(portfolio))
      } yield ExitCode.Success
    }
  }

  def createClient[F[_]: Sync: Monad: Concurrent: ContextShift](): Resource[F, InvestApiClient[F]] = {
    val config = InvestAccessConfig(token = "t.kkxK5DlAIBodw5moQBIDF1zKSMD-Ov4Kfr5hrBSrTRaxOcRTaeSVKYIdiZXsbSuakLyq9fUK0NUe672oItp6xA")
    val errorHandler: InvestApiResponseError => Unit = _ => ()
    AsyncHttpClientCatsBackend.resource().map {
      backend => new InvestApiSttpClient[F](config, backend)(errorHandler)
    }
  }
}