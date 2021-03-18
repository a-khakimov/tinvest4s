package github.ainr.tinvest4s.http.client

import github.ainr.tinvest4s.domain.schemas.{CandleResolution, FIGI}
import github.ainr.tinvest4s.domain._

/**
 * Класс для взаимодейсвия с REST OpenApi Тинькофф Инвестиций
 *
 * @see [[https://tinkoffcreditsystems.github.io/invest-openapi/rest/]]
 * @author [[https://github.com/a-khakimov/]]
 */

trait TinkoffInvestClient[F[_]] {

  /**
   * Получить портфель клиента
   *
   * @return PortfolioResponse - Успешный ответ
   *         TInvestError - Ошибка
   * */
  def getPortfolio: F[Either[InvestClientError, PortfolioResponse]]

  /**
   * Создать лимитную заявку
   *
   * @param figi    FIGI
   * @param request Параметры запроса
   * @return OrdersResponse - Успешный ответ
   *         TInvestError - Ошибка
   * */
  def limitOrder(figi: FIGI, request: LimitOrderRequest): F[Either[InvestClientError, OrdersResponse]]

  /**
   * Создание рыночной заявки
   *
   * @param figi    FIGI
   * @param request Параметры запроса
   * @return OrdersResponse - Успешный ответ
   *         TInvestError - Ошибка
   * */
  def marketOrder(figi: FIGI, request: MarketOrderRequest): F[Either[InvestClientError, OrdersResponse]]

  /**
   * Отмена заявки
   *
   * @param orderId ID заявки
   * @return EmptyResponse - Успешный ответ
   *         TInvestError - Ошибка
   * */
  def cancelOrder(orderId: String): F[Either[InvestClientError, EmptyResponse]]

  /**
   * Получить список акций
   * */
  def stocks(): F[Either[InvestClientError, MarketInstrumentListResponse]]

  /**
   * Получить список облигаций
   * */
  def bonds(): F[Either[InvestClientError, MarketInstrumentListResponse]]

  /**
   * Получить список ETF
   * */
  def etfs(): F[Either[InvestClientError, MarketInstrumentListResponse]]

  /**
   * Получить список валютных пар
   * */
  def currencies(): F[Either[InvestClientError, MarketInstrumentListResponse]]

  /**
   * Получение стакана по FIGI
   *
   * @param figi  FIGI
   * @param depth Глубина стакана
   * */
  def orderbook(figi: FIGI, depth: Int): F[Either[InvestClientError, OrderbookResponse]]

  /**
   * Получение исторических свечей по FIGI
   *
   * @param figi     FIGI
   * @param interval Интервал свечи
   * @param from     Начало временного промежутка
   * @param to       Конец временного промежутка
   * */
  def candles(figi: FIGI, interval: CandleResolution, from: String, to: String): F[Either[InvestClientError, CandlesResponse]]
}
