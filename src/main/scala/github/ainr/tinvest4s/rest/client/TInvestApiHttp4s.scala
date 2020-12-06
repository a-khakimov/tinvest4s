package github.ainr.tinvest4s.rest.client

import cats.MonadError
import cats.effect.{ConcurrentEffect, ContextShift}
import cats.implicits._
import github.ainr.tinvest4s.models.CandleResolution.CandleResolution
import github.ainr.tinvest4s.models.{CandlesResponse, EmptyResponse, LimitOrderRequest, MarketInstrumentListResponse, MarketOrderRequest, OrderbookResponse, OrdersResponse, Payload, PortfolioResponse, TInvestError}
import io.circe.generic.auto._
import org.http4s.Status.Successful
import org.http4s.circe.CirceEntityCodec.{circeEntityDecoder, circeEntityEncoder}
import org.http4s.client.Client
import org.http4s.headers.{Accept, Authorization}
import org.http4s._

/**
  * @todo Добавить параметр для установки режима sandbox и биржевой торговли
  * @todo Избавиться от повторяющихся фрагментов кода
  * {{{
  * case Successful(resp) => resp.as[EmptyResponse].map(Right(_).withLeft[TInvestError])
  * case error => error.as[TInvestError].map(Left(_).withRight[EmptyResponse])
  * }}}
  */
class TInvestApiHttp4s[F[_] : ConcurrentEffect: ContextShift](client: Client[F],
                                                              token: String,
                                                              sandbox: Boolean = true)(
  implicit F: MonadError[F, Throwable]
) extends TInvestApi[F] {

  private lazy val baseUrl: String = "https://api-invest.tinkoff.ru/openapi/sandbox"
  private lazy val auth = Authorization(Credentials.Token(AuthScheme.Bearer, token))
  private lazy val mediaTypeJson = Accept(MediaType.application.json)
  private lazy val baseRequest = Request[F]().putHeaders(auth, mediaTypeJson)


  override def getPortfolio: F[Either[TInvestError, PortfolioResponse]] = {
    Uri.fromString(s"$baseUrl/portfolio") match {
      case Left(e) => Left(TInvestError("", "Wrong url", Payload(Some(e.details), None))).withRight[PortfolioResponse].pure[F]
      case Right(uri) => {
        for {
          result <- client run {
            baseRequest
              .withMethod(Method.GET)
              .withUri(uri)
          } use {
            case Successful(resp) => resp.as[PortfolioResponse].map(Right(_).withLeft[TInvestError])
            case error => error.as[TInvestError].map(Left(_).withRight[PortfolioResponse])
          }
        } yield result
      }
    }
  }

  override def cancelOrder(orderId: String): F[Either[TInvestError, EmptyResponse]] = {
    Uri.fromString(s"$baseUrl/orders/cancel?orderId=$orderId") match {
      case Left(e) => Left(TInvestError("", "Wrong url", Payload(Some(e.details), None))).withRight[EmptyResponse].pure[F]
      case Right(uri) => {
        for {
          result <- client.run(
            baseRequest
              .withMethod(Method.POST)
              .withUri(uri)
          ) use {
            case Successful(resp) => resp.as[EmptyResponse].map(Right(_).withLeft[TInvestError])
            case error => error.as[TInvestError].map(Left(_).withRight[EmptyResponse])
          }
        } yield result
      }
    }
  }

  /**
   *  Создать лимитную заявку
   *  @param figi FIGI
   *  @param request Параметры лимитной заявки
   *  */
  override def limitOrder(figi: String, request: LimitOrderRequest): F[Either[TInvestError, OrdersResponse]] = {
    Uri.fromString(s"$baseUrl/orders/limit-order?figi=$figi") match {
      case Left(e) => Left(TInvestError("", "Wrong url", Payload(Some(e.details), None))).withRight[OrdersResponse].pure[F]
      case Right(uri) => {
        for {
          result <- client.run(
            baseRequest
              .withMethod(Method.POST)
              .withEntity(request)
              .withUri(uri)
          ) use {
            case Successful(resp) => resp.as[OrdersResponse].map(Right(_).withLeft[TInvestError])
            case error => error.as[TInvestError].map(Left(_).withRight[OrdersResponse])
          }
        } yield result
      }
    }
  }

  override def marketOrder(figi: String, request: MarketOrderRequest): F[Either[TInvestError, OrdersResponse]] = {
    Uri.fromString(s"$baseUrl/orders/market-order?figi=$figi") match {
      case Left(e) => Left(TInvestError("", "Wrong url", Payload(Some(e.details), None))).withRight[OrdersResponse].pure[F]
      case Right(uri) => {
        for {
          result <- client run {
            baseRequest
              .withMethod(Method.POST)
              .withEntity(request)
              .withUri(uri)
          } use {
            case Successful(resp) => resp.as[OrdersResponse].map(Right(_).withLeft[TInvestError])
            case error => error.as[TInvestError].map(Left(_).withRight[OrdersResponse])
          }
        } yield result
      }
    }
  }

  private def getMarketInstrumentList(instrument: String): F[Either[TInvestError, MarketInstrumentListResponse]] = {
    Uri.fromString(s"$baseUrl/market/$instrument") match {
      case Left(e) => Left(TInvestError("", "Wrong url", Payload(Some(e.details), None))).withRight[MarketInstrumentListResponse].pure[F]
      case Right(uri) => {
        for {
          result <- client run {
            baseRequest
              .withMethod(Method.GET)
              .withUri(uri)
          } use {
            case Successful(resp) => resp.as[MarketInstrumentListResponse].map(Right(_).withLeft[TInvestError])
            case error => error.as[TInvestError].map(Left(_).withRight[MarketInstrumentListResponse])
          }
        } yield result
      }
    }
  }

  override def stocks(): F[Either[TInvestError, MarketInstrumentListResponse]] = {
    getMarketInstrumentList("stocks")
  }

  override def bonds(): F[Either[TInvestError, MarketInstrumentListResponse]] = {
    getMarketInstrumentList("bonds")
  }

  override def etfs(): F[Either[TInvestError, MarketInstrumentListResponse]] = {
    getMarketInstrumentList("etfs")
  }

  override def currencies(): F[Either[TInvestError, MarketInstrumentListResponse]] = {
    getMarketInstrumentList("currencies")
  }

  override def orderbook(figi: String, depth: Int): F[Either[TInvestError, OrderbookResponse]] = {
    Uri.fromString(s"$baseUrl/market/orderbook?figi=$figi&depth=$depth") match {
      case Left(e) => Left(TInvestError("", "Wrong url", Payload(Some(e.details), None))).withRight[OrderbookResponse].pure[F]
      case Right(uri) => {
        for {
          result <- client run {
            baseRequest
              .withMethod(Method.GET)
              .withUri(uri)
          } use {
            case Successful(resp) => resp.as[OrderbookResponse].map(Right(_).withLeft[TInvestError])
            case error => error.as[TInvestError].map(Left(_).withRight[OrderbookResponse])
          }
        } yield result
      }
    }
  }

  override def candles(figi: String,
                       interval: CandleResolution,
                       from: String,
                       to: String): F[Either[TInvestError, CandlesResponse]] = {
    Uri.fromString(s"$baseUrl/market/candles?figi=$figi&interval=$interval&from=$from&to=$to") match {
      case Left(e) => Left(TInvestError("", "Wrong url", Payload(Some(e.details), None))).withRight[CandlesResponse].pure[F]
      case Right(uri) => {
        for {
          result <- client run {
            baseRequest
              .withMethod(Method.GET)
              .withUri(uri)
          } use {
            case Successful(resp) => resp.as[CandlesResponse].map(Right(_).withLeft[TInvestError])
            case error => error.as[TInvestError].map(Left(_).withRight[CandlesResponse])
          }
        } yield result
      }
    }
  }
}

