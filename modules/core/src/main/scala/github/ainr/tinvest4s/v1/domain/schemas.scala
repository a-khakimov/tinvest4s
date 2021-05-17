package github.ainr.tinvest4s.v1.domain

/* TODO: Корректно структурировать это все */
object schemas {
  type FIGI = String
  type Price = Double
  type TrackingId = String
  type OrderId = String
  type BrokerAccountId = String
  type InstrumentType = String
  type Balance = Double
  type Lots = Int;

  sealed trait Response

  case class MoneyAmount(currency: String, value: Double)

  type Currency = String
  object Currency {
    val RUB: Currency = "RUB"
    val USD: Currency = "USD"
    val EUR: Currency = "EUR"
    val GBP: Currency = "GBP"
    val HKD: Currency = "HKD"
    val CHF: Currency = "CHF"
    val JPY: Currency = "JPY"
    val CNY: Currency = "CNY"
    val TRY: Currency = "TRY"
    val UnknownCurrency: Currency = "UnknownCurrency"
  }

  type Operation = String
  object Operation {
    val Buy: Operation = "Buy"
    val Sell: Operation = "Sell"
  }

  type OrderStatus = String
  object OrderStatus {
    val New: OrderStatus = "New"
    val PartiallyFill: OrderStatus = "PartiallyFill"
    val Fill: OrderStatus = "Fill"
    val Cancelled: OrderStatus = "Cancelled"
    val Replaced: OrderStatus = "Replaced"
    val PendingCancel: OrderStatus = "PendingCancel"
    val Rejected: OrderStatus = "Rejected"
    val PendingReplace: OrderStatus = "PendingReplace"
    val PendingNew: OrderStatus = "PendingNew"
  }

  type CandleResolution = String
  object CandleResolution {
    val `1min` : CandleResolution = "1min"
    val `2min` : CandleResolution = "2min"
    val `3min` : CandleResolution = "3min"
    val `5min` : CandleResolution = "5min"
    val `10min`: CandleResolution = "10min"
    val `15min`: CandleResolution = "15min"
    val `30min`: CandleResolution = "30min"
    val  hour  : CandleResolution = "hour"
    val  day   : CandleResolution = "day"
    val  week  : CandleResolution = "week"
    val  month : CandleResolution = "month"
  }

  case class PortfolioResponse(
    trackingId: TrackingId,
    payload: Portfolio,
    status: String
  ) extends Response

  case class Portfolio(positions: Seq[PortfolioPosition])

  case class PortfolioPosition(
    figi: FIGI,
    ticker: Option[String],
    isin: Option[String],
    instrumentType: InstrumentType,
    balance: Balance,
    blocked: Option[Double],
    expectedYield: Option[MoneyAmount],
    lots: Lots,
    averagePositionPrice: Option[MoneyAmount],
    averagePositionPriceNoNkd: Option[MoneyAmount],
    name: String
  )

  case class MarketOrderRequest(lots: Lots, operation: Operation)
  case class LimitOrderRequest(lots: Lots, operation: Operation, price: Price)
  case class OrderResponse(
    trackingId: String,
    status: String,
    payload: PlacedOrder
  ) extends Response

  case class PlacedOrder(
    orderId: OrderId,
    operation: Operation,
    status: OrderStatus,
    rejectReason: Option[String],
    message: Option[String],
    requestedLots: Lots,
    executedLots: Lots,
    commission: Option[MoneyAmount]
  )

  case class Order(
    orderId: OrderId,
    figi: FIGI,
    operation: Operation,
    status: OrderStatus,
    requestedLots: Lots,
    executedLots: Lots,
    price: Price
  )
}
