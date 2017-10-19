package com.mandrcon.dms2.graphql

import sangria.schema._

object Query {
  val Type = ObjectType(
    "Query",
    "The root query.",
    fields[DMSContext, Unit](
      Field(
        "asdf",
        IntType,
        Some("asdfg"),
        resolve = _.ctx.query.asdf,
      ),
    )
  )
}

class Query(ctx: DMSContext) {

  val asdf = 1
}
