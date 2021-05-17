package v1.api

import github.ainr.tinvest4s.v1.domain.schemas.{Operation, OrderResponse, PlacedOrder}
import github.ainr.tinvest4s.v1.domain.{InvestApiError, InvestApiErrorPayload}
import github.ainr.tinvest4s.v1.http.client.interpreters.InvestApiSttpClient.InvestApiResponseError
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import sttp.client3.HttpError
import sttp.client3.testing.SttpBackendStub
import sttp.model.StatusCode
import v1.api.client.TestInvestApiClient

class MarketOrderMethodSpec extends AnyFlatSpec with Matchers with TestInvestApiClient {

  behavior of "InvestApiClient"

  it should "create market order" in {
    val backend = SttpBackendStub
      .synchronous
      .whenRequestMatches(_ => true)
      .thenRespond(
        """
          |{
          |  "trackingId": "bfbc0bd6607b8d14",
          |  "payload": {
          |      "orderId": "b7278cc7-8d88-412a-a85c-984d5a0c2c4d",
          |      "operation": "Buy",
          |      "status": "Fill",
          |      "requestedLots": "1",
          |      "executedLots": "1"
          |  },
          |  "status": "Ok"
          |}
          |""".stripMargin)


    val limitOrderResponse =
      OrderResponse(
        trackingId = "bfbc0bd6607b8d14",
        status = "Ok",
        payload = PlacedOrder(
          orderId = "b7278cc7-8d88-412a-a85c-984d5a0c2c4d",
          operation = "Buy",
          status = "Fill",
          rejectReason = None,
          message = None,
          requestedLots = 1,
          executedLots = 1,
          commission = None
        )
      )

    val errorHandler: InvestApiResponseError => Unit = _ => ()
    val client = createClient(backend, errorHandler)

    val response = client.marketOrder("BBG005HLSZ23", 1, Operation.Sell)

    response shouldBe Some(limitOrderResponse)
  }

  it should "return error" in {
    val backend = SttpBackendStub
      .synchronous
      .whenRequestMatches(_ => true)
      .thenRespond(
        """
          |{
          |  "trackingId": "b16ed655d2c0205c",
          |  "payload": {
          |    "message": "Some error",
          |    "code": "42"
          |  },
          |  "status": "Ok"
          |}
          |""".stripMargin, StatusCode(500))

    def errorHandler(error: InvestApiResponseError): Unit = {
      error shouldBe HttpError(
        InvestApiError("b16ed655d2c0205c", "Ok", InvestApiErrorPayload(Some("Some error"), Some("42"))),
        StatusCode(500)
      )
    }
    val client = createClient(backend, errorHandler)

    val response = client.marketOrder("BBG005HLSZ23", 1, Operation.Sell)

    response shouldBe None
  }
}
