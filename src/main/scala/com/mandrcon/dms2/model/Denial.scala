package com.mandrcon.dms2.model

import java.sql.Date

object DenialSource extends Enumeration {
  val DMS, Summit, Apollo, Other = Value
}

object DenialCategory extends Enumeration {
  val NICU, MedSurg, PSYCH, Readmit = Value
}

case class Denial(
  id: String = "",
  source: DenialSource.Value = DenialSource.Other,
  category: DenialCategory.Value = DenialCategory.NICU,
  dateAssigned: Option[Date] = None,
  assignedTo: Option[User] = None,
  appeal: Option[Appeal] = None,

  // additional info about the denial
  datesDenied: Option[String] = None,
  denialReason: Option[String] = None,
  pages: Option[Int] = None,
  patientId: Option[String] = None,
  patientName: Option[String] = None,
)
