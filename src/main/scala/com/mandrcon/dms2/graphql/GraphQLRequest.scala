package com.mandrcon.dms2.graphql

import com.twitter.finatra.request.Header


case class GraphQLRequest(
  @Header Authorization: Option[String],
  query: String,
  operationName: Option[String],
  ) {
  def jwt: Option[String] = Authorization.map(_.drop("Bearer ".length))
}
