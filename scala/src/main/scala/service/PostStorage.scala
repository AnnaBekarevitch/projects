package service

import cats.Id
import cats.effect.IO
import cats.syntax.either._
import dao.PostSql
import domain._
import domain.errors._
import doobie._
import doobie.implicits._
import tofu.logging.Logging
import tofu.logging.Logging.Make
import tofu.syntax.logging._

trait PostStorage {
  def list: IO[Either[InternalError, List[Post]]]
  def findById(
      id: PostId
  ): IO[Either[InternalError, Option[Post]]]
  def removeById(id: PostId): IO[Either[AppError, Unit]]
  def create(post: CreatePost): IO[Either[AppError, Post]]
}

object PostStorage {
  private final class Impl(postSql: PostSql, transactor: Transactor[IO])
      extends PostStorage {
    override def list: IO[Either[InternalError, List[Post]]] =
      postSql.listAll
        .transact(transactor)
        .attempt
        .map(_.leftMap(InternalError.apply))
    override def findById(
        id: PostId
    ): IO[Either[InternalError, Option[Post]]] =
      postSql
        .findById(id)
        .transact(transactor)
        .attempt
        .map(_.leftMap(InternalError.apply))

    override def removeById(
        id: PostId
    ): IO[Either[AppError, Unit]] =
      postSql.removeById(id).transact(transactor).attempt.map {
        case Left(th)           => InternalError(th).asLeft
        case Right(Left(error)) => error.asLeft
        case _                  => ().asRight
      }

    override def create(
        post: CreatePost
    ): IO[Either[AppError, Post]] =
      postSql.create(post).transact(transactor).attempt.map {
        case Left(th)           => InternalError(th).asLeft
        case Right(Left(error)) => error.asLeft
        case Right(Right(post)) => post.asRight
      }
  }

  private final class LoggingImpl(storage: PostStorage)(implicit
      logging: Logging[IO]
  ) extends PostStorage {

    private def surroundWithLogs[Error, Res](
        io: IO[Either[Error, Res]]
    )(
        inputLog: String
    )(errorOutputLog: Error => (String, Option[Throwable]))(
        successOutputLog: Res => String
    ): IO[Either[Error, Res]] =
      info"$inputLog" *> io.flatTap {
        case Left(error) =>
          val (logString: String, throwable: Option[Throwable]) =
            errorOutputLog(error)
          throwable.fold(error"$logString")(err =>
            errorCause"$logString"(err)
          )
        case Right(success) => info"${successOutputLog(success)}"
      }

    override def list: IO[Either[InternalError, List[Post]]] =
      surroundWithLogs(storage.list)("Getting all posts")(err =>
        ("Error while getting all posts", Some(err.cause0))
      )(s => s"Got all posts $s")

    override def findById(
        id: PostId
    ): IO[Either[InternalError, Option[Post]]] =
      surroundWithLogs(storage.findById(id))("Finding post by id")(err =>
        (s"Failed to find post by id${err.message}", Some(err.cause0))
      )(success => s"Found post: $success")

    override def removeById(
        id: PostId
    ): IO[Either[AppError, Unit]] =
      surroundWithLogs(storage.removeById(id))("removing post")(err =>
        (err.message, err.cause)
      )(_ => "Delete successful")

    override def create(
                         post: CreatePost
    ): IO[Either[AppError, Post]] =
      surroundWithLogs(storage.create(post))("Creating post")(err =>
        (err.message, err.cause)
      )(success => s"Created post: $success")

  }

  def make(
      sql: PostSql,
      transactor: Transactor[IO]
  ): PostStorage = {
    val logs: Make[IO] = Logging.Make.plain[IO]
    implicit val logging: Id[Logging[IO]] = logs.forService[PostStorage]
    val impl = new Impl(sql, transactor)
    new LoggingImpl(impl)
  }

  doobie.free.connection.WeakAsyncConnectionIO
}
