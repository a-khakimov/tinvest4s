package github.ainr.tinvest4s.websocket.client

import cats.effect.{Concurrent, ConcurrentEffect, ContextShift, Timer}
import cats.syntax.functor._
import github.ainr.tinvest4s.models.CandleResolution.CandleResolution
import github.ainr.tinvest4s.websocket.request.{CandleRequest, InstrumentInfoRequest, OrderBookRequest}
import github.ainr.tinvest4s.websocket.response.{CandleResponse, InstrumentInfoResponse, OrderBookResponse, TInvestWSResponse}
import io.circe.generic.auto._
import io.circe.parser.decode
import io.circe.syntax.EncoderOps
import io.circe.{Decoder, Encoder}
import org.http4s.client.jdkhttpclient.{WSConnectionHighLevel, WSFrame}

trait TInvestWSApi[F[_]] {
  def subscribeCandle(figi: String, interval: CandleResolution): F[Unit]
  def subscribeOrderbook(figi: String, depth: Int): F[Unit] /* 0 < DEPTH <= 20 */
  def subscribeInstrumentInfo(figi: String): F[Unit]

  def unsubscribeCandle(figi: String, interval: CandleResolution): F[Unit]
  def unsubscribeOrderbook(figi: String, depth: Int): F[Unit]
  def unsubscribeInstrumentInfo(figi: String): F[Unit]

  def listen(): F[List[TInvestWSResponse]]
}

trait TInvestWSHandler[F[_]] {
  def handle(response: TInvestWSResponse): F[Unit]
}

class TInvestWSApiHttp4s[F[_] : ConcurrentEffect: Timer: ContextShift : Concurrent]
(connection: WSConnectionHighLevel[F], handler: TInvestWSHandler[F])
  extends TInvestWSApi[F] {

  override def subscribeCandle(figi: String, interval: String): F[Unit] = {
    connection.send {
        WSFrame.Text {
          CandleRequest("candle:subscribe", figi, interval).asJson.noSpaces
        }
      }
  }

  override def subscribeOrderbook(figi: String, depth: Int): F[Unit] = {
    connection.send {
        WSFrame.Text {
          OrderBookRequest("orderbook:subscribe", figi, depth).asJson.noSpaces
        }
      }
  }

  override def subscribeInstrumentInfo(figi: String): F[Unit] = {
    connection.send {
        WSFrame.Text {
          InstrumentInfoRequest("instrument_info:subscribe", figi).asJson.noSpaces
        }
      }
  }

  override def unsubscribeCandle(figi: String, interval: String): F[Unit] = {
    connection.send {
        WSFrame.Text {
          CandleRequest("candle:subscribe", figi, interval).asJson.noSpaces
        }
      }
  }

  override def unsubscribeOrderbook(figi: String, depth: Int): F[Unit] = {
    connection.send {
        WSFrame.Text {
          OrderBookRequest("orderbook:unsubscribe", figi, depth).asJson.noSpaces
        }
      }
  }

  override def unsubscribeInstrumentInfo(figi: String): F[Unit] = {
    connection.send {
        WSFrame.Text {
          InstrumentInfoRequest("instrument_info:unsubscribe", figi).asJson.noSpaces
        }
      }
  }

  def listen(): F[List[TInvestWSResponse]] = {
    connection
      .receiveStream
      .collect { case WSFrame.Text(str, _) => decode[TInvestWSResponse](str) }
      .collect { case Right(response) => response }
      .evalTap { data => handler.handle(data) }
      .compile
      .toList
  }

  implicit val decodeTInvestWSResponse: Decoder[TInvestWSResponse] =
    List[Decoder[TInvestWSResponse]](
      Decoder[CandleResponse].widen,
      Decoder[OrderBookResponse].widen,
      Decoder[InstrumentInfoResponse].widen,
    ).reduceLeft(_ or _)

  implicit val encodeTInvestWSResponse: Encoder[TInvestWSResponse] = Encoder.instance {
    case cr @ CandleResponse(_, _, _) => cr.asJson
    case or @ OrderBookResponse(_, _, _) => or.asJson
    case ir @ InstrumentInfoResponse(_, _, _) => ir.asJson
  }
}
