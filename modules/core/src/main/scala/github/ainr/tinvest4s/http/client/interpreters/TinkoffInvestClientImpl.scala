package github.ainr.tinvest4s.http.client.interpreters

import cats.MonadError
import cats.effect.{ConcurrentEffect, ContextShift}
import cats.implicits._
import github.ainr.tinvest4s.domain._
import github.ainr.tinvest4s.domain.schemas.{CandleResolution, FIGI}
import github.ainr.tinvest4s.http.client.TinkoffInvestClient
import github.ainr.tinvest4s.http.json._
import io.circe.generic.auto._
import org.http4s.Status.Successful
import org.http4s._
import org.http4s.circe.CirceEntityCodec.circeEntityDecoder
import org.http4s.client.Client
import org.http4s.headers.{Accept, Authorization}

/**
  * @todo Добавить параметр для установки режима sandbox и биржевой торговли
  * @todo Избавиться от повторяющихся фрагментов кода
  * {{{
  * case Successful(resp) => resp.as[EmptyResponse].map(Right(_).withLeft[TInvestError])
  * case error => error.as[TInvestError].map(Left(_).withRight[EmptyResponse])
  * }}}
  */
final class TinkoffInvestClientImpl[F[_] : ConcurrentEffect: ContextShift](client: Client[F],
                                                              token: String,
                                                              sandbox: Boolean = true)(
  implicit F: MonadError[F, Throwable]
) extends TinkoffInvestClient[F] {

  private lazy val baseUrl: String = "https://api-invest.tinkoff.ru/openapi/sandbox"
  private lazy val auth = Authorization(Credentials.Token(AuthScheme.Bearer, token))
  private lazy val mediaTypeJson = Accept(MediaType.application.json)
  private lazy val baseRequest = Request[F]().putHeaders(auth, mediaTypeJson)

  override def getPortfolio: F[Either[InvestClientError, PortfolioResponse]] = {
    Uri.fromString(s"$baseUrl/portfolio") match {
      case Left(e) => Left(InvestClientError("", "Wrong url", Payload(Some(e.details), None))).withRight[PortfolioResponse].pure[F]
      case Right(uri) => {
        for {
          result <- client run {
            baseRequest
              .withMethod(Method.GET)
              .withUri(uri)
          } use {
            case Successful(resp) => resp.as[PortfolioResponse].map(Right(_).withLeft[InvestClientError])
            case error => error.as[InvestClientError].map(Left(_).withRight[PortfolioResponse])
          }
        } yield result
      }
    }
  }

  override def cancelOrder(orderId: String): F[Either[InvestClientError, EmptyResponse]] = {
    Uri.fromString(s"$baseUrl/orders/cancel?orderId=$orderId") match {
      case Left(e) => Left(InvestClientError("", "Wrong url", Payload(Some(e.details), None))).withRight[EmptyResponse].pure[F]
      case Right(uri) => {
        for {
          result <- client.run(
            baseRequest
              .withMethod(Method.POST)
              .withUri(uri)
          ) use {
            case Successful(resp) => resp.as[EmptyResponse].map(Right(_).withLeft[InvestClientError])
            case error => error.as[InvestClientError].map(Left(_).withRight[EmptyResponse])
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
  override def limitOrder(figi: FIGI, request: LimitOrderRequest): F[Either[InvestClientError, OrdersResponse]] = {
    Uri.fromString(s"$baseUrl/orders/limit-order?figi=$figi") match {
      case Left(e) => Left(InvestClientError("", "Wrong url", Payload(Some(e.details), None))).withRight[OrdersResponse].pure[F]
      case Right(uri) => {
        for {
          result <- client.run(
            baseRequest
              .withMethod(Method.POST)
              .withEntity(request)
              .withUri(uri)
          ) use {
            case Successful(resp) => resp.as[OrdersResponse].map(Right(_).withLeft[InvestClientError])
            case error => error.as[InvestClientError].map(Left(_).withRight[OrdersResponse])
          }
        } yield result
      }
    }
  }

  override def marketOrder(figi: FIGI, request: MarketOrderRequest): F[Either[InvestClientError, OrdersResponse]] = {
    Uri.fromString(s"$baseUrl/orders/market-order?figi=$figi") match {
      case Left(e) => Left(InvestClientError("", "Wrong url", Payload(Some(e.details), None))).withRight[OrdersResponse].pure[F]
      case Right(uri) => {
        for {
          result <- client run {
            baseRequest
              .withMethod(Method.POST)
              .withEntity(request)
              .withUri(uri)
          } use {
            case Successful(resp) => resp.as[OrdersResponse].map(Right(_).withLeft[InvestClientError])
            case error => error.as[InvestClientError].map(Left(_).withRight[OrdersResponse])
          }
        } yield result
      }
    }
  }

  private def getMarketInstrumentList(instrument: String): F[Either[InvestClientError, MarketInstrumentListResponse]] = {
    Uri.fromString(s"$baseUrl/market/$instrument") match {
      case Left(e) => Left(InvestClientError("", "Wrong url", Payload(Some(e.details), None))).withRight[MarketInstrumentListResponse].pure[F]
      case Right(uri) => {
        for {
          result <- client run {
            baseRequest
              .withMethod(Method.GET)
              .withUri(uri)
          } use {
            case Successful(resp) => resp.as[MarketInstrumentListResponse].map(Right(_).withLeft[InvestClientError])
            case error => error.as[InvestClientError].map(Left(_).withRight[MarketInstrumentListResponse])
          }
        } yield result
      }
    }
  }

  override def stocks(): F[Either[InvestClientError, MarketInstrumentListResponse]] = {
    getMarketInstrumentList("stocks")
  }

  override def bonds(): F[Either[InvestClientError, MarketInstrumentListResponse]] = {
    getMarketInstrumentList("bonds")
  }

  override def etfs(): F[Either[InvestClientError, MarketInstrumentListResponse]] = {
    getMarketInstrumentList("etfs")
  }

  override def currencies(): F[Either[InvestClientError, MarketInstrumentListResponse]] = {
    getMarketInstrumentList("currencies")
  }

  override def orderbook(figi: FIGI, depth: Int): F[Either[InvestClientError, OrderbookResponse]] = {
    Uri.fromString(s"$baseUrl/market/orderbook?figi=$figi&depth=$depth") match {
      case Left(e) => Left(InvestClientError("", "Wrong url", Payload(Some(e.details), None))).withRight[OrderbookResponse].pure[F]
      case Right(uri) => {
        for {
          result <- client run {
            baseRequest
              .withMethod(Method.GET)
              .withUri(uri)
          } use {
            case Successful(resp) => resp.as[OrderbookResponse].map(Right(_).withLeft[InvestClientError])
            case error => error.as[InvestClientError].map(Left(_).withRight[OrderbookResponse])
          }
        } yield result
      }
    }
  }

  override def candles(figi: FIGI,
                       interval: CandleResolution,
                       from: String,
                       to: String): F[Either[InvestClientError, CandlesResponse]] = {
    Uri.fromString(s"$baseUrl/market/candles?figi=$figi&interval=$interval&from=$from&to=$to") match {
      case Left(e) => Left(InvestClientError("", "Wrong url", Payload(Some(e.details), None))).withRight[CandlesResponse].pure[F]
      case Right(uri) => {
        for {
          result <- client run {
            baseRequest
              .withMethod(Method.GET)
              .withUri(uri)
          } use {
            case Successful(resp) => resp.as[CandlesResponse].map(Right(_).withLeft[InvestClientError])
            case error => error.as[InvestClientError].map(Left(_).withRight[CandlesResponse])
          }
        } yield result
      }
    }
  }
}
