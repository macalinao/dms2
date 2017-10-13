package com.mandrcon.dms2.model

import java.sql.Date

case class Appeal(
  id: Int,
  denial: Denial,
  dateSubmitted: Date,
  dateReviewed: Option[Date],
  body: String,
)
