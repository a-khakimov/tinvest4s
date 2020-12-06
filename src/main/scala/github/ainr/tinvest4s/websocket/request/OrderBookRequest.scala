package github.ainr.tinvest4s.websocket.request

/**
 * Запрос для подписки/отписки на стакан
 * @param event Событие подписка(candle:subscribe) или отписка(candle:unsubscribe)
 * @param figi FIGI
 * @param depth Глубина стакана
 * @param request_id ID запроса
 * @todo Типизировать параметр event
 */
case class OrderBookRequest(event: String, figi: String, depth: Int, request_id: Option[String] = None)
  extends TInvestWSRequest
