package com.mandrcon.dms2.graphql

import com.mandrcon.dms2.model._
import org.joda.time.DateTimeZone
import org.joda.time.format.ISODateTimeFormat
import sangria.macros.derive._
import java.sql.Date
import sangria.schema._
import sangria.validation.ValueCoercionViolation
import scala.util.{ Failure, Success, Try }

object GraphQLTypes {
  implicit val SourceEnumType = deriveEnumType[DenialSource.Value]()
  implicit val CategoryEnumType = deriveEnumType[DenialCategory.Value]()

  case object DateCoercionViolation extends ValueCoercionViolation("Date value expected")

  def parseDate(s: String) = Try(Date.valueOf(s)) match {
    case Success(date) => Right(date)
    case Failure(_) => Left(DateCoercionViolation)
  }

  implicit val DateType = ScalarType[Date]("DateTime",
    coerceOutput = (date: Date, _) => sangria.ast.StringValue(date.toString),
    coerceUserInput = {
      case s: String => parseDate(s)
      case _ => Left(DateCoercionViolation)
    },
    coerceInput = {
      case sangria.ast.StringValue(s, _, _) => parseDate(s)
      case _ => Left(DateCoercionViolation)
    },
  )


  implicit val DenialType = deriveObjectType[DMSContext, Denial]()
}
