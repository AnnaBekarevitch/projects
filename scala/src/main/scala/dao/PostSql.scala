package dao

import cats.{Applicative, Monad}
import cats.syntax.applicative._
import cats.syntax.either._
import domain._
import domain.errors._
import doobie._
import doobie.implicits._

trait PostSql {
  def listAll: ConnectionIO[List[Post]]
  def findById(id: PostId): ConnectionIO[Option[Post]]
  def removeById(id: PostId): ConnectionIO[Either[PostNotFound, Unit]]
  def create(post: CreatePost): ConnectionIO[Either[PostAlreadyExists, Post]]
}

object PostSql {

  object sqls {
    val listAllSql: Query0[Post] =
      sql"""
           select *
           from POSTS
      """.query[Post]

    def findByIdSql(id: PostId): Query0[Post] =
      sql"""
           select *
           from POSTS
           where id=${id.value}
      """.query[Post]

    def removeByIdSql(id: PostId): Update0 =
      sql"""
           delete from POSTS
           where id=${id.value}
         """.update

    def insertSql(post: CreatePost): Update0 =
      sql"""
            insert into POSTS (name, remainder_date)
            values (${post.name.value}, ${post.remainderDate.value.toEpochMilli}})
           """.update

    def findByNameAndDate(name: PostName, date: RemainderDate): Query0[Post] =
      sql"select * from POSTS where name=${name.value} and remainder_date=${date.value.toEpochMilli}}"
        .query[Post]
  }

  private final class Impl extends PostSql {

    import sqls._

    override def listAll: ConnectionIO[List[Post]] = listAllSql.to[List]

    override def findById(
        id: PostId
    ): ConnectionIO[Option[Post]] = findByIdSql(id).option

    override def removeById(
        id: PostId
    ): ConnectionIO[Either[PostNotFound, Unit]] = removeByIdSql(id).run.map {
      case 0 => PostNotFound(id).asLeft
      case _ => ().asRight
    }

    override def create(
        post: CreatePost
    ): ConnectionIO[Either[PostAlreadyExists, Post]] =
      findByNameAndDate(post.name, post.remainderDate).option.flatMap {
        case Some(_) => PostAlreadyExists().asLeft[Post].pure[ConnectionIO]
        case None =>
          insertSql(post)
            .withUniqueGeneratedKeys[PostId]("id")
            .map((id: PostId) =>
              Post(id, post.name, post.remainderDate).asRight
            )
      }
  }

  def make: PostSql = new Impl
}
