package github.ainr.tinvest4s.http

import github.ainr.tinvest4s.domain.schemas.{FIGI, TrackingId}
import io.circe._
import io.circe.generic.semiauto._

object json extends JsonCodecs {

}

private[http] trait JsonCodecs {

  implicit val trackingIdDecoder: Decoder[TrackingId] = deriveDecoder[TrackingId]
  implicit val trackingIdEncoder: Encoder[TrackingId] = deriveEncoder[TrackingId]

  implicit val FIGIDecoder: Decoder[FIGI] = deriveDecoder[FIGI]
  implicit val FIGIEncoder: Encoder[FIGI] = deriveEncoder[FIGI]

}
