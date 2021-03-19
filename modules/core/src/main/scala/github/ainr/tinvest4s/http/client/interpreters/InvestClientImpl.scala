package github.ainr.tinvest4s.http.client.interpreters

import cats.MonadError
import github.ainr.tinvest4s.domain._
import github.ainr.tinvest4s.domain.schemas.{CandleResolution, FIGI}
import github.ainr.tinvest4s.http.client.InvestClient

/**
  * @todo Добавить параметр для установки режима sandbox и биржевой торговли
  * @todo Избавиться от повторяющихся фрагментов кода
  * {{{
  * case Successful(resp) => resp.as[EmptyResponse].map(Right(_).withLeft[TInvestError])
  * case error => error.as[TInvestError].map(Left(_).withRight[EmptyResponse])
  * }}}
  */
final class InvestClientImpl[F[_]](
  token: String,
  sandbox: Boolean = true
) extends InvestClient[F] {

  private lazy val baseUrl: String = "https://api-invest.tinkoff.ru/openapi/sandbox"

  override def portfolio: F[Either[InvestClientError, PortfolioResponse]] = {
    //Uri.fromString(s"$baseUrl/portfolio")
    ???
  }

  override def cancelOrder(orderId: String): F[Either[InvestClientError, EmptyResponse]] = {
    //Uri.fromString(s"$baseUrl/orders/cancel?orderId=$orderId")
    ???
  }

  /**
   *  Создать лимитную заявку
   *  @param figi FIGI
   *  @param request Параметры лимитной заявки
   *  */
  override def limitOrder(figi: FIGI, request: LimitOrderRequest): F[Either[InvestClientError, OrdersResponse]] = {
    //Uri.fromString(s"$baseUrl/orders/limit-order?figi=$figi")
    ???
  }

  override def marketOrder(figi: FIGI, request: MarketOrderRequest): F[Either[InvestClientError, OrdersResponse]] = {
    //Uri.fromString(s"$baseUrl/orders/market-order?figi=$figi")
    ???
  }

  private def getMarketInstrumentList(instrument: String): F[Either[InvestClientError, MarketInstrumentListResponse]] = {
    //Uri.fromString(s"$baseUrl/market/$instrument")
    ???
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
    //Uri.fromString(s"$baseUrl/market/orderbook?figi=$figi&depth=$depth")
    ???
  }

  override def candles(figi: FIGI,
                       interval: CandleResolution,
                       from: String,
                       to: String): F[Either[InvestClientError, CandlesResponse]] = {
    //Uri.fromString(s"$baseUrl/market/candles?figi=$figi&interval=$interval&from=$from&to=$to")
    ???
  }
}
