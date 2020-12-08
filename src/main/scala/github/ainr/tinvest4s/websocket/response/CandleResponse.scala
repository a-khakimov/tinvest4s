package github.ainr.tinvest4s.websocket.response

import github.ainr.tinvest4s.models.FIGI.FIGI

/**
 * Формат ответа от Streaming сервера на подписку на свечи
 *
 * @param event Название события
 * @param time Время в формате RFC3339Nano
 * @param payload Структура свечи
 */
case class CandleResponse(event: String,
                          time: String,
                          payload: CandlePayload) extends TInvestWSResponse

/**
 * Структура свечи
 * @param o Цена открытия
 * @param c Цена закрытия
 * @param h Наибольшая цена
 * @param l Наименьшая цена
 * @param v Объем торгов
 * @param time RFC3339
 * @param interval Интервал
 * @param figi FIGI
 */
case class CandlePayload(o: Double,
                         c: Double,
                         h: Double,
                         l: Double,
                         v: Double,
                         time: String,
                         interval: String,
                         figi: FIGI)
