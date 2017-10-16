package com.mandrcon.dms2.model

import java.sql.Date

object Appeal {
}

case class Appeal(
  id: Int,
  denial: Denial,
  dateSubmitted: Date,
  dateReviewed: Option[Date],
  body: Option[String],
)
