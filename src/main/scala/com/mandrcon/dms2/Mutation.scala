package com.mandrcon.dms2

import com.mandrcon.dms2.db.Database
import doobie._, doobie.implicits._
import com.mandrcon.dms2.model._
import monix.eval.Task
import pdi.jwt.Jwt
import sangria.schema._
import cats.implicits._
import sangria.macros.derive._

object Mutation {

  val UsernameArg = Argument("username", StringType, description = "The username.")
  val PasswordArg = Argument("password", StringType, description = "The password.")

  val Type = ObjectType(
    "Mutation",
    "The root mutation.",
    fields[Context, Unit](
      Field(
        "login",
        OptionType(StringType),
        arguments = UsernameArg :: PasswordArg :: Nil,
        resolve = ctx =>
          ctx.ctx.mutation.login(
            ctx.arg(UsernameArg),
            ctx.arg(PasswordArg),
          )
      )
    )
  )

  implicit val sourceEnumType = deriveEnumType[DenialSource.Value]()
  implicit val categoryEnumType = deriveEnumType[DenialCategory.Value]()

  case class CreateDenial(
    source: DenialSource.Value,
    category: DenialCategory.Value,
    datesDenied: Option[String],
    denialReason: Option[String],
    pages: Option[Int],
    patientId: Option[String],
    patientName: Option[String],
  )

  implicit val createDenialType = deriveInputObjectType[CreateDenial]()

}

class Mutation(ctx: Context) {
  import Mutation._

  def login(username: String, password: String): Option[String] = {
    Jwt.encode(s"""{"username": "${username}"}""", "secretKey").some
  }

  def createDenial(input: CreateDenial): Task[Option[Denial]] = {
    for {
      _ <- sql"""
        INSERT INTO dms2.denials(
          dates_denied, denial_reason, pages, patient_id, patient_name
        ) VALUES (
          ${input.datesDenied}, ${input.denialReason}, ${input.pages}, ${input.patientId}, ${input.patientName}
        )
      """.update.run.transact(Database.xa)
    } yield Denial().some
  }

}
