package github.ainr.tinvest4s.domain

import github.ainr.tinvest4s.domain.TradeStatus.TradeStatus
import github.ainr.tinvest4s.domain.schemas.{CandleResolution, FIGI, Price, TrackingId}


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
 * @param minPriceIncrement - Шаг цены
 * @param lot
 * @param minQuantity - Минимальное число инструментов для покупки
 *                    (должно быть не меньше, чем размер лота х количество лотов)
 * @param currency
 * @param name
 * @param `type`
 */
case class MarketInstrument(figi: FIGI,
                            ticker: String,
                            isin: Option[String],
                            minPriceIncrement: Option[Double],
                            lot: Int,
                            minQuantity: Option[Int],
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
 * @param figi - FIGI
 * @param depth - Глубина стакана
 * @param bids
 * @param asks
 * @param tradeStatus
 * @param minPriceIncrement - Шаг цены
 * @param faceValue - Номинал для облигаций
 * @param lastPrice
 * @param closePrice
 * @param limitUp - Верхняя граница цены
 * @param limitDown - Нижняя граница цены
 */
case class Orderbook(figi: FIGI,
                     depth: Int,
                     bids: List[OrderResponse],
                     asks: List[OrderResponse],
                     tradeStatus: TradeStatus,
                     minPriceIncrement: Double,
                     faceValue: Option[Double],
                     lastPrice: Option[Double],
                     closePrice: Option[Double],
                     limitUp: Option[Double],
                     limitDown: Option[Double])

/**
 *
 * @param price
 * @param quantity
 */
case class OrderResponse(price: Price, quantity: Int)

/**
 *
 * @param trackingId
 * @param status
 * @param payload
 */
case class CandlesResponse(trackingId: TrackingId, status: String, payload: Candles)

/**
 *
 * @param figi
 * @param interval
 * @param candles
 */
case class Candles(figi: FIGI, interval: CandleResolution, candles: List[Candle])

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
case class Candle(figi: FIGI, interval: CandleResolution,
                  o: Double, c: Double, h: Double, l: Double, v: Int,
                  time: String)
