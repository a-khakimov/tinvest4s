package github.ainr.tinvest4s.http.error

import github.ainr.tinvest4s.domain.InvestApiError
import github.ainr.tinvest4s.domain.schemas.Response
import sttp.client3.ResponseException

trait ErrorHandler[F[_]] {
  def handle[E, R <: Response](response: Either[ResponseException[InvestApiError, io.circe.Error], R]): F[R]
}
