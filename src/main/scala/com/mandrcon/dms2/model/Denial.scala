package com.mandrcon.dms2.model

import java.sql.Date
import doobie.postgres._, doobie.postgres.implicits._


object DenialSource extends Enumeration {
  val DMS, Summit, Apollo, Other = Value

  implicit val Meta = pgEnum(DenialSource, "dms2.denial_source")
}

object DenialCategory extends Enumeration {
  val NICU, MedSurg, PSYCH, Readmit = Value

  implicit val Meta = pgEnum(DenialCategory, "dms2.denial_category")
}

case class Denial(
  denialId: String = "",
  source: DenialSource.Value = DenialSource.Other,
  category: DenialCategory.Value = DenialCategory.NICU,
  dateAssigned: Option[Date] = None,
  assignedTo: Option[String] = None,

  // additional info about the denial
  datesDenied: Option[String] = None,
  denialReason: Option[String] = None,
  pages: Option[Int] = None,
  patientId: Option[String] = None,
  patientName: Option[String] = None,
)
