package com.mandrcon.dms2.model

import java.sql.Date

object Denial {
  object Source extends Enumeration {
    val DMS, Summit, Apollo, other = Value
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
  datesDenied: String,
  denialReason: String,
  pages: Int,
  patientId: String,
  patientName: String,
)
