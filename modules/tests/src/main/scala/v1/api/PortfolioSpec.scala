package v1.api

import github.ainr.tinvest4s.domain.{InvestApiError, InvestApiErrorPayload}
import github.ainr.tinvest4s.domain.schemas.{Portfolio, PortfolioPosition, PortfolioResponse}
import github.ainr.tinvest4s.http.client.interpreters.InvestApiSttpClient.InvestApiResponseError
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import sttp.client3.HttpError
import sttp.client3.testing.SttpBackendStub
import sttp.model.StatusCode
import v1.api.client.TestInvestApiClient

class PortfolioSpec extends AnyFlatSpec with Matchers with TestInvestApiClient {

  behavior of "InvestApiClient"

  it should "return portfolio" in {
    val backend = SttpBackendStub
      .synchronous
      .whenRequestMatches(_ => true)
      .thenRespond(
        """
          |{
          |  "trackingId": "b16ed655d2c0205c",
          |  "payload": {
          |    "positions": [
          |      {
          |        "figi": "BBG005HLSZ23",
          |        "ticker": "FXUS",
          |        "isin": "IE00BD3QHZ91",
          |        "instrumentType": "Etf",
          |        "balance": 155,
          |        "blocked": 0,
          |        "lots": 155,
          |        "name": "FinEx Акции американских компаний"
          |      }
          |    ]
          |  },
          |  "status": "Ok"
          |}
          |""".stripMargin)


    val portfolioResponse =
      PortfolioResponse(
        "b16ed655d2c0205c",
        Portfolio(
          List(
            PortfolioPosition(
              figi = "BBG005HLSZ23",
              ticker = Some("FXUS"),
              isin = Some("IE00BD3QHZ91"),
              instrumentType = "Etf",
              balance = 155.0,
              blocked = Some(0.0),
              expectedYield = None,
              lots = 155,
              averagePositionPrice = None,
              averagePositionPriceNoNkd = None,
              name = "FinEx Акции американских компаний")
          )
        ),
        "Ok"
      )

    val errorHandler: InvestApiResponseError => Unit = _ => ()
    val client = createClient(backend, errorHandler)

    val portfolio = client.portfolio()

    portfolio shouldBe Some(portfolioResponse)
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

    val portfolio = client.portfolio()

    portfolio shouldBe None
  }
}
