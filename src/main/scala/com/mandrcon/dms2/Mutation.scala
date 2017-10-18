package com.mandrcon.dms2

import pdi.jwt.Jwt
import sangria.schema._
import cats.implicits._

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
}

class Mutation(ctx: Context) {

  def login(username: String, password: String): Option[String] = {
    Jwt.encode(s"""{"username": "${username}"}""", "secretKey").some
  }
}
