package com.mandrcon.dms2

import sangria.schema._

object Query {
  val Type = ObjectType(
    "Query",
    "The root query.",
    fields[Context, Unit](
      Field(
        "asdf",
        IntType,
        Some("asdfg"),
        resolve = _.ctx.query.asdf,
      ),
    )
  )
}

class Query(ctx: Context) {

  val asdf = 1
}
