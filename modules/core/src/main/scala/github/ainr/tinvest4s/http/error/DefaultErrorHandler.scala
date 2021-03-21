package github.ainr.tinvest4s.http.error
import cats.Monad
import cats.implicits._
import github.ainr.tinvest4s.domain.{InvestApiError, schemas}
import io.circe
import sttp.client3.{DeserializationException, HttpError, ResponseException}

final class DefaultErrorHandler[F[_]: Monad] extends ErrorHandler[F] {

  override def handle[E, R <: schemas.Response]
  (
    response: Either[ResponseException[InvestApiError, circe.Error], R]
  ): F[R] = {
    response match {
      case Right(success) => success.pure[F]
      case Left(DeserializationException(body, error)) => ??? // error deserializing spotify response
      case Left(HttpError(body, statusCode)) => ??? // http error
      case Left(error) => ??? // other error
    }
  }

}
