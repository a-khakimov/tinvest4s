import java.net.http.HttpClient

import cats.effect.{ExitCode, IO, IOApp, Resource, Sync}
import cats.implicits.catsSyntaxApplicativeId
import github.ainr.tinvest4s.models.CandleResolution
import github.ainr.tinvest4s.websocket.client.{TInvestWSApi, TInvestWSApiHttp4s, TInvestWSAuthorization, TInvestWSHandler}
import github.ainr.tinvest4s.websocket.response.{CandleResponse, InstrumentInfoResponse, OrderBookResponse, TInvestWSResponse}
import org.http4s.client.jdkhttpclient.{JdkWSClient, WSConnectionHighLevel}

class SomeHandler[F[_]: Sync] extends TInvestWSHandler[F] {
  override def handle(response: TInvestWSResponse): F[Unit] = {
    response match {
      case CandleResponse(_, _, candle) => ???
      case OrderBookResponse(_, _, orderBook) => ???
      case InstrumentInfoResponse(_, _, instrumentInfo) => ???
    }
  }
}


object Tinvest4sWebsocketExample extends IOApp {

  type F[+T] = IO[T]

  def resources(): Resource[F, WSConnectionHighLevel[F]] = {
    for {
      wsClient <- JdkWSClient[F](HttpClient.newHttpClient())
        .connectHighLevel(
          TInvestWSAuthorization()
            .withToken("token")
        )
    } yield wsClient
  }

  override def run(args: List[String]): F[ExitCode] = {
    for {
      _ <- resources().use {
        wsClient => {
          implicit val handler: TInvestWSHandler[F] = new SomeHandler[F]()
          implicit val tinvestWSApi: TInvestWSApi[F] = new TInvestWSApiHttp4s[F](wsClient, handler)
          for {
            _ <- tinvestWSApi.subscribeCandle("BBG005HLSZ23", CandleResolution.`1min`)
          } yield ()
        }
      }
    } yield ExitCode.Success
  }
}
