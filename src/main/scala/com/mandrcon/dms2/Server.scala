package com.mandrcon.dms2

import com.twitter.finatra.http.HttpServer
import com.twitter.finatra.http.routing.HttpRouter
import monix.execution.Scheduler.Implicits.global
import com.mandrcon.dms2.graphql.GraphQLController


class Server extends HttpServer {

  override val defaultFinatraHttpPort = s":8080"

  override def configureHttp(router: HttpRouter) {
    router.add(new GraphQLController(this))
  }
}
