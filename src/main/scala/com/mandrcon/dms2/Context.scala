package com.mandrcon.dms2

case class Context(jwt: Option[String]) {
  lazy val query = new Query(this)
  lazy val mutation = new Mutation(this)
}
