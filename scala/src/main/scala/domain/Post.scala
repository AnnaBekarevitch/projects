package domain

import derevo.circe.{decoder, encoder}
import derevo.derive
import doobie.Read
import sttp.tapir.Schema
import sttp.tapir.derevo.schema
import tofu.logging.derivation._

@derive(loggable, encoder, decoder, schema)
final case class CreatePost(name: PostName, remainderDate: RemainderDate)

@derive(loggable, encoder, decoder, schema)
final case class Post(id: PostId, name: PostName, reminderDate: RemainderDate)
