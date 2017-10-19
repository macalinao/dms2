package com.mandrcon.dms2.graphql

case class DMSContext(jwt: Option[String]) {
  lazy val query = new Query(this)
  lazy val mutation = new Mutation(this)
}
