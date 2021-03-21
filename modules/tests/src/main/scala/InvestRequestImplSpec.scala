import github.ainr.tinvest4s.config.access.InvestAccessConfig
import github.ainr.tinvest4s.domain.schemas.{Portfolio, PortfolioPosition, PortfolioResponse}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import sttp.client3.testing.SttpBackendStub

class InvestRequestImplSpec extends AnyFlatSpec with Matchers  {

  val testingBackend = SttpBackendStub
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

  val config = InvestAccessConfig("")
  //val request = new InvestRequestImpl(config)
  val result =
    PortfolioResponse(
      "b16ed655d2c0205c",
      Portfolio(
        List(
          PortfolioPosition(
            "BBG005HLSZ23",
            Some("FXUS"),
            Some("IE00BD3QHZ91"),
            "Etf",
            155.0,
            Some(0.0),
            None,
            155,
            None,None,
            "FinEx Акции американских компаний")
        )
      ),
      "Ok"
    )

  //"InvestRequestImpl" must "work" in {
    //testingBackend.send(request.portfolio).body shouldBe Right(result)
  //}
}
