package github.ainr.tinvest4s.models

import github.ainr.tinvest4s.models.CandleResolution.CandleResolution

case class MarketInstrumentListResponse(trackingId: String, status: String, payload: MarketInstrumentList)

case class MarketInstrumentList(total: Int, instruments: List[MarketInstrument])

case class MarketInstrument(figi: String,
                            ticker: String,
                            isin: Option[String],
                            minPriceIncrement: Option[Double], /* Шаг цены */
                            lot: Int,
                            minQuantity: Option[Int], /* Минимальное число инструментов для покупки должно
                                                         быть не меньше, чем размер лота х количество лотов */
                            currency: Option[String],
                            name: String,
                            `type`: String)

case class OrderbookResponse(trackingId: String, status: String, payload: Orderbook)
case class Orderbook(figi: String,
                     depth: Int,
                     bids: List[OrderResponse],
                     asks: List[OrderResponse],
                     tradeStatus: String,
                     minPriceIncrement: Double, // Шаг цены
                     faceValue: Option[Double], // Номинал для облигаций
                     lastPrice: Option[Double],
                     closePrice: Option[Double],
                     limitUp: Option[Double],   // Верхняя граница цены
                     limitDown: Option[Double]) // Нижняя граница цены
case class OrderResponse(price: Double, quantity: Int)


case class CandlesResponse(trackingId: String, status: String, payload: Candles)
case class Candles(figi: String, interval: CandleResolution, candles: List[Candle])
case class Candle(figi: String, interval: CandleResolution,
                  o: Double, c: Double, h: Double, l: Double, v: Int,
                  time: String)

object CandleResolution {
  type CandleResolution = String
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

