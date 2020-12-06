package github.ainr.tinvest4s.websocket.request

/**
 * Запрос для подписки/отписки на информацию от инструменте
 * @param event Событие подписка(candle:subscribe) или отписка(candle:unsubscribe)
 * @param figi FIGI
 * @param request_id ID запроса
 * @todo Типизировать параметр event
 */
case class InstrumentInfoRequest(event: String, figi: String, request_id: Option[String] = None)
  extends TInvestWSRequest
