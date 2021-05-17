package github.ainr.tinvest4s.v1.config

object access {
  type Token = String
  case class InvestAccessConfig(
    token: Token,
    isSandbox: Boolean = true
  )
}
