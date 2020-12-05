package github.ainr.tinvest4s.websocket.client

import org.http4s.client.jdkhttpclient.WSRequest
import org.http4s.implicits.http4sLiteralsSyntax
import org.http4s.{Header, Headers}

case class TInvestWSAuthorization() {
  private val wsUri = uri"wss://api-invest.tinkoff.ru/openapi/md/v1/md-openapi/ws"
  def withToken(token: String): WSRequest = {
    WSRequest(wsUri, Headers.of(Header("Authorization", s"Bearer $token")))
  }
}
