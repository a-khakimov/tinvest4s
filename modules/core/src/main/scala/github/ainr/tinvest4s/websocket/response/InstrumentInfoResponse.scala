package github.ainr.tinvest4s.websocket.response

import github.ainr.tinvest4s.models.FIGI.FIGI

/**
 * Формат ответа от Streaming сервера на подписку на информацию об инструменте
 *
 * @param event Название события
 * @param time Время в формате RFC3339Nano
 * @param payload Структура с информацией по инструменту
 */
case class InstrumentInfoResponse(event: String,
                                  time: String,
                                  payload: InstrumentInfoPayload) extends TInvestWSResponse

/**
 * Структура с информацией по инструменту
 * @param trade_status Статус торгов
 * @param min_price_increment Шаг цены
 * @param lot Лот
 * @param accrued_interest НКД. Возвращается только для бондов
 * @param limit_up Верхняя граница заявки. Возвращается только для RTS инструментов
 * @param limit_down Нижняя граница заявки. Возвращается только для RTS инструментов
 * @param figi FIGI
 */
case class InstrumentInfoPayload(trade_status: String,
                                 min_price_increment: Double,
                                 lot: Double,
                                 accrued_interest: Option[Double],
                                 limit_up: Option[Double],
                                 limit_down: Option[Double],
                                 figi: FIGI)
