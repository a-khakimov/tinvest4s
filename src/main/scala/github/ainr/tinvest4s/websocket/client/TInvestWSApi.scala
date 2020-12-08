package github.ainr.tinvest4s.websocket.client

import cats.effect.{Concurrent, ConcurrentEffect, ContextShift, Timer}
import cats.syntax.functor._
import github.ainr.tinvest4s.models.CandleResolution.CandleResolution
import github.ainr.tinvest4s.models.FIGI.FIGI
import github.ainr.tinvest4s.websocket.request.{CandleRequest, InstrumentInfoRequest, OrderBookRequest}
import github.ainr.tinvest4s.websocket.response.{CandleResponse, InstrumentInfoResponse, OrderBookResponse, TInvestWSResponse}
import io.circe.generic.auto._
import io.circe.parser.decode
import io.circe.syntax.EncoderOps
import io.circe.{Decoder, Encoder}
import org.http4s.client.jdkhttpclient.{WSConnectionHighLevel, WSFrame}

/**
 * Класс для взаимодейсвия со Streaming OpenApi Тинькофф Инвестиций
 * @see [[https://tinkoffcreditsystems.github.io/invest-openapi/marketdata/]]
 * @author [[https://github.com/a-khakimov/]]
 */
trait TInvestWSApi[F[_]] {

  /**
   * Подписка на свечи
   * @param figi FIGI
   * @param interval Интервал
   * */
  def subscribeCandle(figi: FIGI, interval: CandleResolution): F[Unit]

  /**
   * Подписка на стакан
   * @param figi FIGI
   * @param depth Глубина стакана (0 < depth <= 20)
   * */
  def subscribeOrderbook(figi: FIGI, depth: Int): F[Unit]

  /**
   * Подписка на информацию об инструменте
   * @param figi FIGI
   * */
  def subscribeInstrumentInfo(figi: FIGI): F[Unit]

  /**
   * Отписка от свечей
   * @param figi FIGI
   * @param interval Интервал
   * */
  def unsubscribeCandle(figi: FIGI, interval: CandleResolution): F[Unit]

  /**
   * Отписка от стакана
   * @param figi FIGI
   * @param depth Глубина стакана (0 < depth <= 20)
   * */
  def unsubscribeOrderbook(figi: FIGI, depth: Int): F[Unit]

  /**
   * Отписка от информации об инструменте
   * @param figi FIGI
   * */
  def unsubscribeInstrumentInfo(figi: FIGI): F[Unit]

  /**
   * Начать выполнение клиента
   * */
  def listen(): F[List[TInvestWSResponse]]
}

/**
 * Класс для получения и обработки событий со Streaming сервера.
 * Пример использования:
 *
 * {{{
 *  class StreamingEvents[F[_]: Sync] extends TInvestWSHandler[F] {
 *    override def handle(response: TInvestWSResponse): F[Unit] = {
 *      response match {
 *        case CandleResponse(_, _, candle) => ???
 *        case OrderBookResponse(_, _, orderBook) => ???
 *        case InstrumentInfoResponse(_, _, instrumentInfo) => ???
 *      }
 *    }
 *  }
 * }}}
 */
trait TInvestWSHandler[F[_]] {
  /**
   * Метод будет вызван по наступлению события
   * @param response Параметр будет содержать необходимые данные
   * */
  def handle(response: TInvestWSResponse): F[Unit]
}

class TInvestWSApiHttp4s[F[_] : ConcurrentEffect: Timer: ContextShift : Concurrent]
(connection: WSConnectionHighLevel[F], handler: TInvestWSHandler[F])
  extends TInvestWSApi[F] {

  override def subscribeCandle(figi: FIGI, interval: String): F[Unit] = {
    connection.send {
        WSFrame.Text {
          CandleRequest("candle:subscribe", figi, interval).asJson.noSpaces
        }
      }
  }

  override def subscribeOrderbook(figi: FIGI, depth: Int): F[Unit] = {
    connection.send {
        WSFrame.Text {
          OrderBookRequest("orderbook:subscribe", figi, depth).asJson.noSpaces
        }
      }
  }

  override def subscribeInstrumentInfo(figi: FIGI): F[Unit] = {
    connection.send {
        WSFrame.Text {
          InstrumentInfoRequest("instrument_info:subscribe", figi).asJson.noSpaces
        }
      }
  }

  override def unsubscribeCandle(figi: FIGI, interval: String): F[Unit] = {
    connection.send {
        WSFrame.Text {
          CandleRequest("candle:subscribe", figi, interval).asJson.noSpaces
        }
      }
  }

  override def unsubscribeOrderbook(figi: FIGI, depth: Int): F[Unit] = {
    connection.send {
        WSFrame.Text {
          OrderBookRequest("orderbook:unsubscribe", figi, depth).asJson.noSpaces
        }
      }
  }

  override def unsubscribeInstrumentInfo(figi: FIGI): F[Unit] = {
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
