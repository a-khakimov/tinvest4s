package v1.api.client

import github.ainr.tinvest4s.v1.config.access.InvestAccessConfig
import github.ainr.tinvest4s.v1.http.client.InvestApiClient
import github.ainr.tinvest4s.v1.http.client.interpreters.InvestApiSttpClient
import github.ainr.tinvest4s.v1.http.client.interpreters.InvestApiSttpClient.InvestApiResponseError
import sttp.capabilities.WebSockets
import sttp.client3.Identity
import sttp.client3.testing.SttpBackendStub

trait TestInvestApiClient {
  def createClient(
    backend: SttpBackendStub[Identity, WebSockets],
    errorHandler: InvestApiResponseError => Unit
  ): InvestApiClient[Identity] = {
    val config = InvestAccessConfig("token")
    new InvestApiSttpClient[Identity](config, backend)(errorHandler)
  }
}
