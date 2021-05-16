package github.ainr.tinvest4s.http.client.interpreters

import cats.Monad
import cats.implicits._
import github.ainr.tinvest4s.config.access.{InvestAccessConfig, Token}
import github.ainr.tinvest4s.domain.InvestApiError
import github.ainr.tinvest4s.domain.schemas.{BrokerAccountId, PortfolioResponse}
import github.ainr.tinvest4s.http.client.InvestApiClient
import github.ainr.tinvest4s.http.error.ErrorHandler
import github.ainr.tinvest4s.http.json._
import sttp.client3.circe.asJsonEither
import sttp.client3.{SttpBackend, _}

final class InvestApiSttpClient[F[_]: Monad]
(
  config: InvestAccessConfig,
  backend: SttpBackend[F, Any],
  errorHandler: ErrorHandler[F]
) extends InvestApiClient[F] {

  private lazy val token: Token = config.token

  private lazy val baseUrl: String = {
    s"https://api-invest.tinkoff.ru/openapi/${
      if (config.isSandbox) "sandbox"
      else ""
    }"
  }

  override def portfolio(brokerAccountId: Option[BrokerAccountId] = None): F[PortfolioResponse] = {
    val uri = brokerAccountId match {
      case Some(id) => uri"$baseUrl/portfolio?brokerAccountId=$id"
      case _ => uri"$baseUrl/portfolio"
    }

    basicRequest
      .get(uri)
      .auth.bearer(token)
      .response(asJsonEither[InvestApiError, PortfolioResponse])
      .send(backend)
      .flatMap { r =>
        errorHandler.handle[InvestApiError, PortfolioResponse](r.body)
      }
  }
}
