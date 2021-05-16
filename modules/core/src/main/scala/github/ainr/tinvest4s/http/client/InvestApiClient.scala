package github.ainr.tinvest4s.http.client

import github.ainr.tinvest4s.domain.schemas.{BrokerAccountId, FIGI, Lots, Operation, OrderResponse, PortfolioResponse, Price}

trait InvestApiClient[F[_]] {

  def portfolio(brokerAccountId: Option[BrokerAccountId] = None): F[Option[PortfolioResponse]]

  def limitOrder(
    figi: FIGI,
    lots: Lots,
    operation: Operation,
    price: Price,
    brokerAccountId: Option[BrokerAccountId] = None
  ): F[Option[OrderResponse]]

  def marketOrder(
    figi: FIGI,
    lots: Lots,
    operation: Operation,
    brokerAccountId: Option[BrokerAccountId] = None
  ): F[Option[OrderResponse]]
}
