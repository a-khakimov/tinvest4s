package github.ainr.tinvest4s.domain

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

  sealed trait Currency { def name: String }
  object Currency {
    case class RUB() extends Currency() { override def name: String = "RUB" }
    case class USD() extends Currency() { override def name: String = "USD" }
    case class EUR() extends Currency() { override def name: String = "EUR" }
    case class GBP() extends Currency() { override def name: String = "GBP" }
    case class HKD() extends Currency() { override def name: String = "HKD" }
    case class CHF() extends Currency() { override def name: String = "CHF" }
    case class JPY() extends Currency() { override def name: String = "JPY" }
    case class CNY() extends Currency() { override def name: String = "CNY" }
    case class TRY() extends Currency() { override def name: String = "TRY" }
    case class UnknownCurrency() extends Currency() { override def name: String = "UnknownCurrency" }
  }

  sealed trait Operation {
    def name: String
    override def toString: String = name
  }
  object Operation {
    case class Buy()  extends Operation { override def name: String = "Buy"  }
    case class Sell() extends Operation { override def name: String = "Sell" }
  }


  object OrderStatus {
    case class New()            extends OrderStatus { override def name: String = "New"            }
    case class PartiallyFill()  extends OrderStatus { override def name: String = "PartiallyFill"  }
    case class Fill()           extends OrderStatus { override def name: String = "Fill"           }
    case class Cancelled()      extends OrderStatus { override def name: String = "Cancelled"      }
    case class Replaced()       extends OrderStatus { override def name: String = "Replaced"       }
    case class PendingCancel()  extends OrderStatus { override def name: String = "PendingCancel"  }
    case class Rejected()       extends OrderStatus { override def name: String = "Rejected"       }
    case class PendingReplace() extends OrderStatus { override def name: String = "PendingReplace" }
    case class PendingNew()     extends OrderStatus { override def name: String = "PendingNew"     }
  }
  sealed trait OrderStatus {
    def name: String
    override def toString: String = name
  }

  object CandleResolution {
    case class `1min` () extends CandleResolution { override def value: String = "1min"  }
    case class `2min` () extends CandleResolution { override def value: String = "2min"  }
    case class `3min` () extends CandleResolution { override def value: String = "3min"  }
    case class `5min` () extends CandleResolution { override def value: String = "5min"  }
    case class `10min`() extends CandleResolution { override def value: String = "10min" }
    case class `15min`() extends CandleResolution { override def value: String = "15min" }
    case class `30min`() extends CandleResolution { override def value: String = "30min" }
    case class  hour  () extends CandleResolution { override def value: String = "hour"  }
    case class  day   () extends CandleResolution { override def value: String = "day"   }
    case class  week  () extends CandleResolution { override def value: String = "week"  }
    case class  month () extends CandleResolution { override def value: String = "month" }
  }
  sealed trait CandleResolution { def value: String }

  case class PortfolioResponse(trackingId: TrackingId, payload: Portfolio, status: String) extends Response

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
  case class OrderResponse(trackingId: String, status: String, payload: PlacedOrder) extends Response

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
    //operation: Operation,
    status: OrderStatus,
    requestedLots: Lots,
    executedLots: Lots,
    price: Price
  )
}
