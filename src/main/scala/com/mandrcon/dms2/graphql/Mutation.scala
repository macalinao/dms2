package com.mandrcon.dms2.graphql

import com.mandrcon.dms2.db.Database
import doobie._, doobie.implicits._
import com.mandrcon.dms2.model._
import io.circe.Decoder
import monix.eval.Task
import monix.execution.Scheduler
import pdi.jwt.Jwt
import sangria.schema._
import cats.implicits._
import sangria.macros.derive._
import sangria.marshalling.circe._
import io.circe.generic.semiauto._


object Mutation {
  import GraphQLTypes._

  case class CreateDenial(
    source: DenialSource.Value,
    category: DenialCategory.Value,
    datesDenied: Option[String],
    denialReason: Option[String],
    pages: Option[Int],
    patientId: Option[String],
    patientName: Option[String],
  )

  implicit val denialSourceDecoder = Decoder.enumDecoder(DenialSource)
  implicit val denialCategoryDecoder = Decoder.enumDecoder(DenialCategory)
  implicit val fooDecoder: Decoder[CreateDenial] = deriveDecoder[CreateDenial]
  implicit val CreateDenialType = deriveInputObjectType[CreateDenial]()

  val UsernameArg = Argument("username", StringType, description = "The username.")
  val PasswordArg = Argument("password", StringType, description = "The password.")

  val CreateDenialArg = Argument("input", CreateDenialType, description = "Denial data.")

  def Type(implicit sched: Scheduler) = ObjectType(
    "Mutation",
    "The root mutation.",
    fields[DMSContext, Unit](
      Field(
        "login",
        OptionType(StringType),
        arguments = UsernameArg :: PasswordArg :: Nil,
        resolve = ctx =>
        ctx.ctx.mutation.login(
          ctx.arg(UsernameArg),
          ctx.arg(PasswordArg),
        )
      ),
      Field(
        "create_denial",
        OptionType(DenialType),
        arguments = CreateDenialArg :: Nil,
        resolve = ctx =>
          ctx.ctx.mutation.createDenial(
            ctx.arg(CreateDenialArg),
          ).runAsync
      ),
    )
  )

}

class Mutation(ctx: DMSContext) {
  import Mutation._

  def login(username: String, password: String): Option[String] = {
    Jwt.encode(s"""{"username": "${username}"}""", "secretKey").some
  }

  def createDenial(input: CreateDenial): Task[Denial] = {
    sql"""
      INSERT INTO dms2.denials(
          source, category, dates_denied, denial_reason, pages, patient_id, patient_name
      ) VALUES (
        ${input.source}, ${input.category}, ${input.datesDenied}, ${input.denialReason},
        ${input.pages}, ${input.patientId}, ${input.patientName}
      )""".update.withUniqueGeneratedKeys[Denial](
        "denial_id",
        "source",
        "category",
        "date_assigned",
        "assigned_to",
        "dates_denied",
        "denial_reason",
        "pages",
        "patient_id",
        "patient_name"
    ).transact(Database.xa)
  }

}
