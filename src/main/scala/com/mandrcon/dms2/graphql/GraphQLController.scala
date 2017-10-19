package com.mandrcon.dms2.graphql

import io.circe.syntax._
import com.twitter.finatra.http.Controller
import com.mandrcon.dms2.Server
import com.twitter.finatra.request.Header
import monix.execution.Scheduler
import sangria.execution.{ ErrorWithResolver, QueryAnalysisError }
import sangria.execution.deferred.DeferredResolver
import sangria.execution.{ Executor, QueryReducer }
import sangria.parser.QueryParser
import sangria.renderer.SchemaRenderer
import sangria.schema.Schema
import scala.concurrent.{ ExecutionContext, Future }


class GraphQLController(server: Server)(implicit sched: Scheduler) extends Controller {
  val intern = true

  val schema = Schema(
    Query.Type, Some(Mutation.Type),
  )

  get("/") { _: Any =>
    response.ok
  }

  /**
    * GraphQL endpoint. We use GET instead of POST to enable Cloudflare caching.
    */
  post("/graphql") { req: GraphQLRequest =>
    val reducers = if (intern) {
      Nil
    } else {
      QueryReducer.rejectIntrospection[DMSContext]() :: Nil
    }

    val fut = for {
      queryAst <- Future.fromTry(QueryParser.parse(req.query))
      result <- Executor.execute(
        schema = schema,
        queryAst = queryAst,
        userContext = new DMSContext(req.jwt),
        // variables = req.vars,
        operationName = req.operationName,
        // deferredResolver = DeferredResolver.fetchers(schema.staticTransformer.staticFetcher),
        queryReducers = reducers,
      )
    } yield {
      response.ok
        .header("Access-Control-Allow-Origin", "*")
        .json(result)
    }

    fut.recover {
      case error: QueryAnalysisError => {
        response
          .badRequest
          .header("Access-Control-Allow-Origin", "*")
          .json(Map("error" -> error.resolveError))
      }

      case error: ErrorWithResolver => {
        response
          .internalServerError
          .header("Access-Control-Allow-Origin", "*")
          .json(Map("error" -> error.resolveError))
      }
    }
  }

  options("/graphql") { _: Any =>
    response.ok
      .header("Access-Control-Allow-Origin", "*")
      .header("Access-Control-Allow-Methods", "POST")
      .header("Access-Control-Allow-Headers", "Content-Type")
  }

  get("/render-schema") { _: Any =>
    if (intern) {
      response.ok.body(SchemaRenderer.renderSchema(schema))
    } else {
      response.forbidden
    }
  }

}
