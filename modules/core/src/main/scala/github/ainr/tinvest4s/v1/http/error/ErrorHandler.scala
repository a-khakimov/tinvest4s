package github.ainr.tinvest4s.v1.http.error

import github.ainr.tinvest4s.v1.domain.InvestApiError
import github.ainr.tinvest4s.v1.domain.schemas.Response
import sttp.client3.ResponseException

trait ErrorHandler[F[_]] {
  def handle[R <: Response](response: Either[ResponseException[InvestApiError, Throwable], R], defaultValue: R): F[R]
}
