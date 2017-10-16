package com.mandrcon.dms2.db

import doobie._, doobie.implicits._
import cats._, cats.data._, cats.effect._, cats.implicits._
import monix.eval.Task

object Database {

  val xa = Transactor.fromDriverManager[Task](
    "org.postgresql.Driver", "jdbc:postgresql:dms2", "ian", "asdf1234",
  )

}
