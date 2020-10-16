package syntax

import scala.concurrent.Future
import zio._

object ZioSyntax {
  implicit final class FutureOps[A](val future: Future[A]) extends AnyVal {
    def asZio: Task[A] = ZIO.fromFuture(_ => future)
  }

  implicit final class ZioOps[E, A](val effect: ZIO[ZEnv, E, A]) extends AnyVal {
    def runSync(): Exit[E, A] =
      zio.Runtime.default.unsafeRunSync(effect)
  }

  implicit final class TaskOps[E <: Throwable, A](val effect: ZIO[ZEnv, E, A]) extends AnyVal {
    def asFuture: Future[A] =
      zio.Runtime.default.unsafeRunToFuture(effect)
    def runTask: A =
      zio.Runtime.default.unsafeRunTask(effect)
  }

  implicit final class UioOps[A](val effect: UIO[A]) extends AnyVal {
    def runSync(): A =
      zio.Runtime.default.unsafeRunSync(effect).toEither.toOption.get
  }
}
