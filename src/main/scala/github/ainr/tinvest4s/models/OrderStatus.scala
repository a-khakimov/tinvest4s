package github.ainr.tinvest4s.models

object OrderStatus {
  type OrderStatus = String
  val New = "New"
  val PartiallyFill = "PartiallyFill"
  val Fill = "Fill"
  val Cancelled = "Cancelled"
  val Replaced = "Replaced"
  val PendingCancel = "PendingCancel"
  val Rejected = "Rejected"
  val PendingReplace = "PendingReplace"
  val PendingNew = "PendingNew"
}
