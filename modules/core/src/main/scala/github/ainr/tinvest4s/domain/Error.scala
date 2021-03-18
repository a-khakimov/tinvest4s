package github.ainr.tinvest4s.domain

/**
 *
 * @param trackingId
 * @param status
 * @param payload
 */
case class InvestClientError(trackingId: String, status: String, payload: Payload) extends Throwable
case class Payload(message: Option[String], code: Option[String])

case class EmptyPayload()
case class EmptyResponse(trackingId: String, status: String, payload: EmptyPayload)
