package com.mandrcon.dms2.model

import java.sql.Date

object Denial {
  object Source extends Enumeration {
    val DMS, Summit, Apollo, Other = Value
  }

  object Category extends Enumeration {
    val NICU, MedSurg, PSYCH, Readmit = Value
  }
}
import Denial._

case class Denial(
  id: Int,
  source: Source.Value,
  category: Category.Value,
  dateAssigned: Option[Date],
  assignedTo: Option[User],
  appeal: Option[Appeal],

  // additional info about the denial
  datesDenied: Option[String],
  denialReason: Option[String],
  pages: Option[Int],
  patientId: Option[String],
  patientName: Option[String],
)
