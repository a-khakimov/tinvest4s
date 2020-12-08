package github.ainr.tinvest4s.websocket.request

import github.ainr.tinvest4s.models.FIGI.FIGI

/**
 * Запрос для подписки/отписки на информацию от инструменте
 *
 * @param event Событие подписка(candle:subscribe) или отписка(candle:unsubscribe)
 * @param figi FIGI
 * @param request_id ID запроса
 * @todo Типизировать параметр event
 */
case class InstrumentInfoRequest(event: String, figi: FIGI, request_id: Option[String] = None)
  extends TInvestWSRequest
