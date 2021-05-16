package github.ainr.tinvest4s.http.client

import github.ainr.tinvest4s.domain.schemas.{BrokerAccountId, PortfolioResponse}

trait InvestApiClient[F[_]] {
  def portfolio(brokerAccountId: Option[BrokerAccountId] = None): F[Option[PortfolioResponse]]
}
