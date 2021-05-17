package github.ainr.tinvest4s.v1.http

import github.ainr.tinvest4s.v1.domain.schemas._
import github.ainr.tinvest4s.v1.domain.{InvestApiError, InvestApiErrorPayload}
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import io.circe.{Decoder, Encoder}

object json extends JsonCodecs {

}

private[http] trait JsonCodecs {

  implicit val moneyAmountDecoder: Decoder[MoneyAmount] = deriveDecoder
  implicit val moneyAmountEncoder: Encoder[MoneyAmount] = deriveEncoder

  implicit val portfolioPositionDecoder: Decoder[PortfolioPosition] = deriveDecoder
  implicit val portfolioPositionEncoder: Encoder[PortfolioPosition] = deriveEncoder

  implicit val portfolioDecoder: Decoder[Portfolio] = deriveDecoder
  implicit val portfolioEncoder: Encoder[Portfolio] = deriveEncoder

  implicit val portfolioResponseDecoder: Decoder[PortfolioResponse] = deriveDecoder
  implicit val portfolioResponseEncoder: Encoder[PortfolioResponse] = deriveEncoder

  implicit val placedOrderDecoder: Decoder[PlacedOrder] = deriveDecoder
  implicit val placedOrderEncoder: Encoder[PlacedOrder] = deriveEncoder

  implicit val orderResponseDecoder: Decoder[OrderResponse] = deriveDecoder
  implicit val orderResponseEncoder: Encoder[OrderResponse] = deriveEncoder

  implicit val limitOrderRequestDecoder: Decoder[LimitOrderRequest] = deriveDecoder
  implicit val limitOrderRequestEncoder: Encoder[LimitOrderRequest] = deriveEncoder

  implicit val marketOrderRequestDecoder: Decoder[MarketOrderRequest] = deriveDecoder
  implicit val marketOrderRequestEncoder: Encoder[MarketOrderRequest] = deriveEncoder

  implicit val investApiErrorPayloadDecoder: Decoder[InvestApiErrorPayload] = deriveDecoder
  implicit val investClientErrorDecoder: Decoder[InvestApiError] = deriveDecoder
}
