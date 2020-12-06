package github.ainr.tinvest4s.models

import github.ainr.tinvest4s.models.CandleResolution.CandleResolution
import github.ainr.tinvest4s.models.TradeStatus.TradeStatus


case class MarketInstrumentListResponse(trackingId: String, status: String, payload: MarketInstrumentList)

/**
 *
 * @param total
 * @param instruments
 */
case class MarketInstrumentList(total: Int, instruments: List[MarketInstrument])

/**
 *
 * @param figi
 * @param ticker
 * @param isin
 * @param minPriceIncrement
 * @param lot
 * @param minQuantity
 * @param currency
 * @param name
 * @param `type`
 */
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

/**
 *
 * @param trackingId
 * @param status
 * @param payload
 */
case class OrderbookResponse(trackingId: String, status: String, payload: Orderbook)

/**
 *
 * @param figi
 * @param depth
 * @param bids
 * @param asks
 * @param tradeStatus
 * @param minPriceIncrement
 * @param faceValue
 * @param lastPrice
 * @param closePrice
 * @param limitUp
 * @param limitDown
 */
case class Orderbook(figi: String,
                     depth: Int,
                     bids: List[OrderResponse],
                     asks: List[OrderResponse],
                     tradeStatus: TradeStatus,
                     minPriceIncrement: Double, // Шаг цены
                     faceValue: Option[Double], // Номинал для облигаций
                     lastPrice: Option[Double],
                     closePrice: Option[Double],
                     limitUp: Option[Double],   // Верхняя граница цены
                     limitDown: Option[Double]) // Нижняя граница цены

/**
 *
 * @param price
 * @param quantity
 */
case class OrderResponse(price: Double, quantity: Int)

/**
 *
 * @param trackingId
 * @param status
 * @param payload
 */
case class CandlesResponse(trackingId: String, status: String, payload: Candles)

/**
 *
 * @param figi
 * @param interval
 * @param candles
 */
case class Candles(figi: String, interval: CandleResolution, candles: List[Candle])

/**
 *
 * @param figi
 * @param interval
 * @param o
 * @param c
 * @param h
 * @param l
 * @param v
 * @param time
 */
case class Candle(figi: String, interval: CandleResolution,
                  o: Double, c: Double, h: Double, l: Double, v: Int,
                  time: String)
