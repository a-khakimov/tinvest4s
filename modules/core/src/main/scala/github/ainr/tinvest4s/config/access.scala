package github.ainr.tinvest4s.config

object access {
  type Token = String
  case class InvestAccessConfig(
    token: Token,
    isSandbox: Boolean = true
  )
}
