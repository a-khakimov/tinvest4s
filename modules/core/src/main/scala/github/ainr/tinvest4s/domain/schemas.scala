package github.ainr.tinvest4s.domain

object schemas {

  case class FIGI(value: String)
  case class Price(value: Double)

  case class Currency(value: String)
  object Currency {
    val RUB = "RUB"
    val USD = "USD"
    val EUR = "EUR"
    val GBP = "GBP"
    val HKD = "HKD"
    val CHF = "CHF"
    val JPY = "JPY"
    val CNY = "CNY"
    val TRY = "TRY"
  }

  case class Operation(value: String)
  object Operation {
    val Buy = "Buy"
    val Sell = "Sell"
  }

  case class OrderStatus(value: String)
  object OrderStatus {
    val New = "New"
    val PartiallyFill = "PartiallyFill"
    val Fill = "Fill"
    val Cancelled = "Cancelled"
    val Replaced = "Replaced"
    val PendingCancel = "PendingCancel"
    val Rejected = "Rejected"
    val PendingReplace = "PendingReplace"
    val PendingNew = "PendingNew"
  }

  case class CandleResolution(value: String)
  object CandleResolution {
    val `1min` = "1min"
    val `2min` = "2min"
    val `3min` = "3min"
    val `5min` = "5min"
    val `10min` = "10min"
    val `15min` = "15min"
    val `30min` = "30min"
    val hour = "hour"
    val day = "day"
    val week = "week"
    val month = "month"
  }

  case class TrackingId(value: String)

}
