import java.util.concurrent.Executors

import cats.effect.{ExitCode, IO, IOApp, Resource, Sync}
import github.ainr.tinvest4s.rest.client.{TInvestApi, TInvestApiHttp4s}
import org.http4s.client.Client
import org.http4s.client.blaze.BlazeClientBuilder

import scala.concurrent.ExecutionContext

object Tinvest4sRestExample extends IOApp {

  type F[+T] = IO[T]

  def resources(): Resource[F, Client[F]] = {
    val ec = ExecutionContext.fromExecutor(Executors.newSingleThreadExecutor())
    for {
      httpClient <- BlazeClientBuilder[F](ec).resource
    } yield httpClient
  }

  override def run(args: List[String]): F[ExitCode] = {
    for {
      _ <- resources().use {
        case httpClient => {
          implicit val tinvestApi: TInvestApi[F] = new TInvestApiHttp4s[F](httpClient, "token")
          for {
            portfolio <- tinvestApi.getPortfolio
            _ <- Sync[F].delay {
              println(portfolio)
            }
          } yield ()
        }
      }
    } yield ExitCode.Success
  }
}
