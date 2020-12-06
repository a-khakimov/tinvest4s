package github.ainr.tinvest4s.websocket.request

/**
 * Запрос для подписки/отписки на свечу
 * @param event Событие подписка(candle:subscribe) или отписка(candle:unsubscribe)
 * @param figi FIGI
 * @param interval Интервал
 * @param request_id ID запроса
 * @todo Типизировать параметр event
 * @todo Типизировать параметр interval
 */
case class CandleRequest(event: String, figi: String, interval: String, request_id: Option[String] = None)
  extends TInvestWSRequest