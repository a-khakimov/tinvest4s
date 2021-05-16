package github.ainr.tinvest4s.test

import cats.implicits.catsStdShowForString
import github.ainr.tinvest4s.config.access.InvestAccessConfig
import github.ainr.tinvest4s.http.client.interpreters.InvestApiSttpClient
import sttp.client3.httpclient.zio.HttpClientZioBackend
import zio._
import zio.interop.catz._

// TODO: ???
/*
object ZioBackendExample extends zio.App {

  val layer = HttpClientZioBackend().flatMap {
    backend =>
      val config = InvestAccessConfig(token = "t.kkxK5DlAIBodw5moQBIDF1zKSMD-Ov4Kfr5hrBSrTRaxOcRTaeSVKYIdiZXsbSuakLyq9fUK0NUe672oItp6xA")
      val errorHandler = new DefaultErrorHandler()
      val client = new InvestApiSttpClient[Task](config, backend, errorHandler)

      for {
        _ <- console.putStrLn("hui")
        p = client.portfolio()
        _ <- console.putStrLn(p.toString)
      } yield ()
  }.toLayer


  override def run(args: List[String]): URIO[zio.ZEnv, ExitCode] = {
    def z = ZIO.runtime[zio.ZEnv].provideLayer(layer)

    for {
      _ <- z
    } yield ()

    ???
  }
}
 */