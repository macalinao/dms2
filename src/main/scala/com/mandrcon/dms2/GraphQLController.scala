package com.mandrcon.dms2

import com.twitter.finatra.http.Controller


class GraphQLController extends Controller {

  get("/") { _: Any =>
    response.ok
  }

}
