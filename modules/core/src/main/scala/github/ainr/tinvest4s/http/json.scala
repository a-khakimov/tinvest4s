package github.ainr.tinvest4s.http

import github.ainr.tinvest4s.domain.schemas.{Operation, _}
import github.ainr.tinvest4s.domain.{InvestApiError, InvestApiErrorPayload}
import io.circe.{Decoder, Encoder}
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}

object json extends JsonCodecs {

}

private[http] trait JsonCodecs {


  implicit val currencyAmountDecoder: Decoder[Currency] = deriveDecoder[Currency]
  implicit val currencyAmountEncoder: Encoder[Currency] = deriveEncoder[Currency]

  implicit val moneyAmountDecoder: Decoder[MoneyAmount] = deriveDecoder
  implicit val moneyAmountEncoder: Encoder[MoneyAmount] = deriveEncoder

  implicit val portfolioPositionDecoder: Decoder[PortfolioPosition] = deriveDecoder
  implicit val portfolioPositionEncoder: Encoder[PortfolioPosition] = deriveEncoder

  implicit val portfolioDecoder: Decoder[Portfolio] = deriveDecoder
  implicit val portfolioEncoder: Encoder[Portfolio] = deriveEncoder

  implicit val operationDecoder: Decoder[Operation] = deriveDecoder
  implicit val operationEncoder: Encoder[Operation] = deriveEncoder

  implicit val portfolioResponseDecoder: Decoder[PortfolioResponse] = deriveDecoder
  implicit val portfolioResponseEncoder: Encoder[PortfolioResponse] = deriveEncoder

  implicit val orderStatusDecoder: Decoder[OrderStatus] = deriveDecoder
  implicit val orderStatusEncoder: Encoder[OrderStatus] = deriveEncoder

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
