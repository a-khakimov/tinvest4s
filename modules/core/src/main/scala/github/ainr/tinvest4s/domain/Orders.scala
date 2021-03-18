package github.ainr.tinvest4s.domain

import github.ainr.tinvest4s.domain.schemas._

/**
 *
 * @param lots
 * @param operation
 */
case class MarketOrderRequest(lots: Int, operation: Operation)

/**
 *
 * @param lots
 * @param operation
 * @param price
 */
case class LimitOrderRequest(lots: Int, operation: Operation, price: Price)

/**
 *
 * @param trackingId
 * @param status
 * @param payload
 */
case class OrdersResponse(trackingId: String, status: String, payload: PlacedOrder)

/**
 *
 * @param orderId
 * @param operation
 * @param status
 * @param rejectReason
 * @param message
 * @param requestedLots
 * @param executedLots
 * @param commission
 */
case class PlacedOrder(orderId: String,
                       operation: Operation,
                       status: OrderStatus,
                       rejectReason: Option[String],
                       message: Option[String],
                       requestedLots: Int,
                       executedLots: Int,
                       commission: Option[MoneyAmount])

/**
 *
 * @param orderId
 * @param figi
 * @param operation
 * @param status
 * @param requestedLots
 * @param executedLots
 * @param price
 */
case class Order(orderId: String,
                 figi: FIGI,
                 operation: Operation,
                 status: OrderStatus,
                 requestedLots: Int,
                 executedLots: Int,
                 price: Price)

case class MoneyAmount(currency: Currency, value: Double)
