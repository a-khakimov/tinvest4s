package github.ainr.tinvest4s.domain

/**
 * @param trackingId
 * @param status
 * @param payload
 */
case class InvestApiError(trackingId: String, status: String, payload: InvestApiErrorPayload)
case class InvestApiErrorPayload(message: Option[String], code: Option[String])

case class EmptyPayload()
case class EmptyResponse(trackingId: String, status: String, payload: EmptyPayload)
