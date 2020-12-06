package github.ainr.tinvest4s.websocket.response

/**
 * Формат ответа от Streaming сервера на подписку на стакан
 * @param event Название события
 * @param time Время в формате RFC3339Nano
 * @param payload Структура со стаканом
 */
case class OrderBookResponse(event: String,
                             time: String, // RFC3339Nano
                             payload: OrderBookPayload) extends TInvestWSResponse

/**
 * Структура со стаканом
 * @param depth Глубина стакана
 * @param bids Массив `[Цена, количество]` предложений цены
 * @param asks Массив `[Цена, количество]` запросов цены
 * @param figi FIGI
 */
case class OrderBookPayload(depth: Int,
                            bids: Seq[(Double, Double)],
                            asks: Seq[(Double, Double)],
                            figi: String)
