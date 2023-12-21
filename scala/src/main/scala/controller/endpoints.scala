package controller

import domain._
import domain.errors._
import sttp.tapir._
import sttp.tapir.generic.auto._
import sttp.tapir.json.circe._

object endpoints {

  val listPosts: PublicEndpoint[RequestContext, AppError, List[Post], Any] =
    endpoint.get
      .in("posts")
      .in(header[RequestContext]("X-Request-Id"))
      .errorOut(jsonBody[AppError])
      .out(jsonBody[List[Post]])

  val findPostById
      : PublicEndpoint[(PostId, RequestContext), AppError, Option[Post], Any] =
    endpoint.get
      .in("post" / path[PostId])
      .in(header[RequestContext]("X-Request-Id"))
      .errorOut(jsonBody[AppError])
      .out(jsonBody[Option[Post]])

  val removePost
      : PublicEndpoint[(PostId, RequestContext), AppError, Unit, Any] = {
    endpoint.delete
      .in("post" / path[PostId])
      .in(header[RequestContext]("X-Request-Id"))
      .errorOut(jsonBody[AppError])
  }

  val createPost
      : PublicEndpoint[(RequestContext, CreatePost), AppError, Post, Any] =
    endpoint.post
      .in("post")
      .in(header[RequestContext]("X-Request-Id"))
      .in(jsonBody[CreatePost])
      .errorOut(jsonBody[AppError])
      .out(jsonBody[Post])
}
