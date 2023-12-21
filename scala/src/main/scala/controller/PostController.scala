package controller

import cats.effect.IO
import domain.errors.AppError
import service.PostStorage
import sttp.tapir.server.ServerEndpoint
//import tofu.syntax.feither._

trait PostController {
  def listAllPosts: ServerEndpoint[Any, IO]
  def findPostById: ServerEndpoint[Any, IO]
  def removePostById: ServerEndpoint[Any, IO]
  def createPost: ServerEndpoint[Any, IO]

  def all: List[ServerEndpoint[Any, IO]]
}

object PostController {
  final private class Impl(storage: PostStorage) extends PostController {

    override val listAllPosts: ServerEndpoint[Any, IO] =
      endpoints.listPosts.serverLogic(ctx =>
        storage.list.map(_.left.map[AppError](identity))
//          storage.list.leftMapIn(identity[AppError])
      )

    override val findPostById: ServerEndpoint[Any, IO] =
      endpoints.findPostById.serverLogic { case (postId, ctx) =>
        storage.findById(postId).map(_.left.map[AppError](identity))
      }

    override val removePostById: ServerEndpoint[Any, IO] =
      endpoints.removePost.serverLogic { case (postId, ctx) =>
        storage.removeById(postId)
      }

    override val createPost: ServerEndpoint[Any, IO] =
      endpoints.createPost.serverLogic { case (context, post) =>
        storage.create(post)
      }

    override val all: List[ServerEndpoint[Any, IO]] =
      List(listAllPosts, findPostById, removePostById, createPost)
  }

  def make(storage: PostStorage): PostController = new Impl(storage)
}
