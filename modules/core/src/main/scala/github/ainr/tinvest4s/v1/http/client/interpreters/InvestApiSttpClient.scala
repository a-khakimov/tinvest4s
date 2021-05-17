package github.ainr.tinvest4s.v1.http.client.interpreters

import cats.Monad
import cats.syntax.all._
import github.ainr.tinvest4s.v1.config.access.{InvestAccessConfig, Token}
import github.ainr.tinvest4s.v1.domain.schemas.{Response => _, _}
import github.ainr.tinvest4s.v1.domain.{InvestApiError, schemas}
import github.ainr.tinvest4s.v1.http.client.InvestApiClient
import github.ainr.tinvest4s.v1.http.client.interpreters.InvestApiSttpClient.InvestApiResponseError
import github.ainr.tinvest4s.v1.http.json._
import io.circe
import sttp.client3._
import sttp.client3.circe._

/*
You should create custom error handler
For example:
```scala
def errorHandler(error: InvestApiResponseError): Unit = {
  error match {
    case DeserializationException(body, error) => ??? // error deserializing response
    case HttpError(body, statusCode) => ???           // http error
    case error => ???                                 // other error
  }
}
```
 */

final class InvestApiSttpClient[F[_]: Monad]
(
  config: InvestAccessConfig,
  backend: SttpBackend[F, Any]
)(
  errorHandler: InvestApiResponseError => Unit = _ => ()
) extends InvestApiClient[F] {

  private lazy val token: Token = config.token

  private lazy val baseUrl: String = {
    s"https://api-invest.tinkoff.ru/openapi/${
      if (config.isSandbox) "sandbox"
      else ""
    }"
  }

  private def handle[R](response: Response[Either[InvestApiResponseError, R]]): Option[R] = {
    response.body match {
      case Right(r) => Some(r)
      case Left(error) => {
        errorHandler(error)
        None
      }
    }
  }

  def portfolio(
    brokerAccountId: Option[BrokerAccountId] = None
  ): F[Option[PortfolioResponse]] = {
    val uri = brokerAccountId
      .map(id => uri"$baseUrl/portfolio?brokerAccountId=$id")
      .getOrElse(uri"$baseUrl/portfolio")

    basicRequest
      .get(uri)
      .auth.bearer(token)
      .response(asJsonEither[InvestApiError, PortfolioResponse])
      .send(backend)
      .map(handle)
  }

  override def limitOrder(
    figi: FIGI,
    lots: Lots,
    operation: Operation,
    price: Price,
    brokerAccountId: Option[BrokerAccountId]
  ): F[Option[OrderResponse]] = {
    val uri = brokerAccountId
      .map(id => uri"$baseUrl/orders/limit-order?figi=$figi&brokerAccountId=$id")
      .getOrElse(uri"$baseUrl/orders/limit-order?figi=$figi")

    val requestBody = LimitOrderRequest(lots, operation, price)

    basicRequest
      .post(uri)
      .auth.bearer(token)
      .body(requestBody)
      .response(asJsonEither[InvestApiError, OrderResponse])
      .send(backend)
      .map(handle)
  }

  override def marketOrder(
    figi: FIGI,
    lots: Lots,
    operation: Operation,
    brokerAccountId: Option[BrokerAccountId]
  ): F[Option[OrderResponse]] = {
    val uri = brokerAccountId
      .map(id => uri"$baseUrl/orders/market-order?figi=$figi&brokerAccountId=$id")
      .getOrElse(uri"$baseUrl/orders/market-order?figi=$figi")

    val requestBody = MarketOrderRequest(lots, operation)

    basicRequest
      .post(uri)
      .auth.bearer(token)
      .body(requestBody)
      .response(asJsonEither[InvestApiError, OrderResponse])
      .send(backend)
      .map(handle)
  }
}

object InvestApiSttpClient {

  type InvestApiResponseError = ResponseException[InvestApiError, circe.Error]
}
