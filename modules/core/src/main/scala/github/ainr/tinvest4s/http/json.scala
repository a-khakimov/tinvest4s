package github.ainr.tinvest4s.http

import github.ainr.tinvest4s.domain.{InvestApiError, InvestApiErrorPayload}
import github.ainr.tinvest4s.domain.schemas.{Currency, MoneyAmount, Portfolio, PortfolioPosition, PortfolioResponse, TrackingId}
import io.circe.generic.semiauto._
import io.circe._

object json extends JsonCodecs {

}

private[http] trait JsonCodecs {

  implicit val currencyAmountDecoder: Decoder[Currency] = deriveDecoder
  implicit val currencyAmountEncoder: Encoder[Currency] = deriveEncoder

  implicit val moneyAmountDecoder: Decoder[MoneyAmount] = deriveDecoder
  implicit val moneyAmountEncoder: Encoder[MoneyAmount] = deriveEncoder

  implicit val portfolioPositionDecoder: Decoder[PortfolioPosition] = deriveDecoder
  implicit val portfolioPositionEncoder: Encoder[PortfolioPosition] = deriveEncoder

  implicit val portfolioDecoder: Decoder[Portfolio] = deriveDecoder
  implicit val portfolioEncoder: Encoder[Portfolio] = deriveEncoder

  implicit val portfolioResponseDecoder: Decoder[PortfolioResponse] = deriveDecoder
  implicit val portfolioResponseEncoder: Encoder[PortfolioResponse] = deriveEncoder

  implicit val investApiErrorPayloadDecoder: Decoder[InvestApiErrorPayload] = deriveDecoder
  implicit val investClientErrorDecoder: Decoder[InvestApiError] = deriveDecoder

}
