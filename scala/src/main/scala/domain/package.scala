import cats.data.ReaderT
import cats.effect.IO
import derevo.circe.{decoder, encoder}
import derevo.derive
import doobie.Read
import io.estatico.newtype.macros.newtype
import sttp.tapir.{Codec, CodecFormat, Schema}
import tofu.logging.derivation._

import java.time.Instant

package object domain {

  @derive(loggable, encoder, decoder)
  @newtype
  case class PostId(value: Long)
  object PostId {
    implicit val read: Read[PostId] = Read[Long].map(PostId.apply)
    implicit val schema: Schema[PostId] =
      Schema.schemaForLong.map(long => Some(PostId(long)))(_.value)
    implicit val codec: Codec[String, PostId, CodecFormat.TextPlain] =
      Codec.long.map(PostId(_))(_.value)
  }

  @derive(loggable, encoder, decoder)
  @newtype
  case class PostName(value: String)
  object PostName {
    implicit val read: Read[PostName] = Read[String].map(PostName.apply)
    implicit val schema: Schema[PostName] =
      Schema.schemaForString.map(string => Some(PostName(string)))(_.value)
  }

  @derive(loggable, encoder, decoder)
  @newtype
  case class RemainderDate(value: Instant)
  object RemainderDate {
    implicit val read: Read[RemainderDate] =
      Read[Long].map(n => RemainderDate(Instant.ofEpochMilli(n)))

    implicit val schema: Schema[RemainderDate] =
      Schema.schemaForLong.map(long =>
        Some(RemainderDate(Instant.ofEpochMilli(long)))
      )(_.value.toEpochMilli)
  }
}
