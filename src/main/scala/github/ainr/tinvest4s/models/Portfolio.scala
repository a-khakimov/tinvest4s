package github.ainr.tinvest4s.models

import github.ainr.tinvest4s.models.FIGI.FIGI

/**
 *
 * @param trackingId
 * @param payload
 * @param status
 */
case class PortfolioResponse(trackingId: String, payload: Portfolio, status: String)

/**
 *
 * @param positions
 */
case class Portfolio(positions: Seq[PortfolioPosition])

/**
 *
 * @param figi
 * @param ticker
 * @param isin
 * @param instrumentType
 * @param balance
 * @param blocked
 * @param expectedYield
 * @param lots
 * @param averagePositionPrice
 * @param averagePositionPriceNoNkd
 * @param name
 */
case class PortfolioPosition(
  figi: FIGI,
  ticker: Option[String],
  isin: Option[String],
  instrumentType: String,
  balance: Double,
  blocked: Option[Double],
  expectedYield: Option[MoneyAmount],
  lots: Int,
  averagePositionPrice: Option[MoneyAmount],
  averagePositionPriceNoNkd: Option[MoneyAmount],
  name: String
)
