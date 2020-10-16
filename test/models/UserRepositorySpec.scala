package models

import zio.ZLayer
import zio.test.Assertion.equalTo
import zio.test._

object UserRepositorySpec extends DefaultRunnableSpec {
  def spec = suite("UserRepository")(
    testM("test get user") {
      for {
        user <- UserRepository.getUser
      } yield assert(user)(equalTo("User from localhost:666"))
    }.provideCustomLayer(testDatabase >>> UserRepository.live)
  )

  val testDatabase = ZLayer.succeed(new Database {
    override def connectionString: String = "localhost:666"
  })
}
