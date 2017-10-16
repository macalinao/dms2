package com.mandrcon.dms2

import com.twitter.finatra.http.HttpServer
import com.twitter.finatra.http.routing.HttpRouter


class Server extends HttpServer {

  override val defaultFinatraHttpPort = s":8080"

  override def configureHttp(router: HttpRouter) {
    router.add[GraphQLController]
  }
}
