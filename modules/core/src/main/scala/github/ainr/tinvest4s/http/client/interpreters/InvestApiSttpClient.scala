package github.ainr.tinvest4s.http.client.interpreters

import cats.Monad
import cats.implicits._
import github.ainr.tinvest4s.config.access.{InvestAccessConfig, Token}
import github.ainr.tinvest4s.domain.InvestApiError
import github.ainr.tinvest4s.domain.schemas.{BrokerAccountId, PortfolioResponse}
import github.ainr.tinvest4s.http.client.InvestApiClient
import github.ainr.tinvest4s.http.client.interpreters.InvestApiSttpClient.InvestApiResponseError
import github.ainr.tinvest4s.http.json._
import io.circe
import sttp.client3.circe.asJsonEither
import sttp.client3.{ResponseException, SttpBackend, _}

/*
You should create custom error handler
For example:
```scala
error match {
  case Left(DeserializationException(body, error)) => ??? // error deserializing response
  case Left(HttpError(body, statusCode)) => ??? // http error
  case Left(error) => ??? // other error
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
}

object InvestApiSttpClient {

  type InvestApiResponseError = ResponseException[InvestApiError, circe.Error]

}
