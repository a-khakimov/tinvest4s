package github.ainr.tinvest4s.rest.client

import github.ainr.tinvest4s.models.CandleResolution.CandleResolution
import github.ainr.tinvest4s.models.FIGI.FIGI
import github.ainr.tinvest4s.models.{CandlesResponse, EmptyResponse, LimitOrderRequest, MarketInstrumentListResponse, MarketOrderRequest, OrderbookResponse, OrdersResponse, PortfolioResponse, TInvestError}


/**
 * Класс для взаимодейсвия с REST OpenApi Тинькофф Инвестиций
 * @see [[https://tinkoffcreditsystems.github.io/invest-openapi/rest/]]
 * @author [[https://github.com/a-khakimov/]]
 */

trait TInvestApi[F[_]] {

  /**
   * Получить портфель клиента
   * @return PortfolioResponse - Успешный ответ
   *         TInvestError - Ошибка
   * */
  def getPortfolio: F[Either[TInvestError, PortfolioResponse]]

  /**
   *  Создать лимитную заявку
   *
   *  @param figi FIGI
   *  @param request Параметры запроса
   *  @return OrdersResponse - Успешный ответ
   *          TInvestError - Ошибка
   *  */
  def limitOrder(figi: FIGI, request: LimitOrderRequest): F[Either[TInvestError, OrdersResponse]]

  /**
   * Создание рыночной заявки
   *
   * @param figi FIGI
   * @param request Параметры запроса
   * @return OrdersResponse - Успешный ответ
   *         TInvestError - Ошибка
   * */
  def marketOrder(figi: FIGI, request: MarketOrderRequest): F[Either[TInvestError, OrdersResponse]]

  /**
   * Отмена заявки
   *
   * @param orderId ID заявки
   * @return EmptyResponse - Успешный ответ
   *         TInvestError - Ошибка
   * */
  def cancelOrder(orderId: String): F[Either[TInvestError, EmptyResponse]]

  /**
   * Получить список акций
   * */
  def stocks(): F[Either[TInvestError, MarketInstrumentListResponse]]

  /**
   * Получить список облигаций
   * */
  def bonds(): F[Either[TInvestError, MarketInstrumentListResponse]]

  /**
   * Получить список ETF
   * */
  def etfs(): F[Either[TInvestError, MarketInstrumentListResponse]]

  /**
   * Получить список валютных пар
   * */
  def currencies(): F[Either[TInvestError, MarketInstrumentListResponse]]

  /**
   * Получение стакана по FIGI
   * @param figi FIGI
   * @param depth Глубина стакана
   * */
  def orderbook(figi: FIGI, depth: Int): F[Either[TInvestError, OrderbookResponse]]

  /**
   * Получение исторических свечей по FIGI
   * @param figi FIGI
   * @param interval Интервал свечи
   * @param from Начало временного промежутка
   * @param to Конец временного промежутка
   * */
  def candles(figi: FIGI, interval: CandleResolution, from: String, to: String): F[Either[TInvestError, CandlesResponse]]
}
