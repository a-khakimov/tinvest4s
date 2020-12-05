package github.ainr.tinvest4s.models

import github.ainr.tinvest4s.models.Currency.Currency
import github.ainr.tinvest4s.models.Operation.Operation
import github.ainr.tinvest4s.models.OrderStatus.OrderStatus

case class MarketOrderRequest(lots: Int, operation: Operation)

case class LimitOrderRequest(lots: Int, operation: Operation, price: Double)

case class OrdersResponse(trackingId: String, status: String, payload: PlacedOrder)

case class PlacedOrder(orderId: String,
                       operation: Operation,
                       status: OrderStatus,
                       rejectReason: Option[String],
                       message: Option[String],
                       requestedLots: Int,
                       executedLots: Int,
                       commission: Option[MoneyAmount])

case class Order(orderId: String,
                 figi: String,
                 operation: Operation,
                 status: OrderStatus,
                 requestedLots: Int,
                 executedLots: Int,
                 price: Double)

case class MoneyAmount(currency: Currency, value: Double)

