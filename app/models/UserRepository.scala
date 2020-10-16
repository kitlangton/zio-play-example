package models
import javax.inject.{Inject, Singleton}
import play.api.Application
import zio._

// Controller
// UserService @Inject(ZioComponents)

object UserRepository {
  type UserRepository = Has[Service]

  trait Service {
    def getUser: Task[String]
  }

  def getUser: ZIO[UserRepository, Throwable, String] = ZIO.accessM[UserRepository](_.get.getUser)

  def testDatabase: ULayer[Has[Database]] = ???

  def application(app: Application): ULayer[Has[Application]] =
    ZLayer.succeed(app)

  def realDatabase: ZLayer[Has[Application], Nothing, Has[Database]] =
    ZLayer.fromService { (application: Application) =>
      application.injector.instanceOf(classOf[Database])
    }

  val live: ZLayer[Has[Database], Nothing, UserRepository] =
    ZLayer.fromService((database: Database) => UserRepositoryLive(database))
}

case class UserRepositoryLive(database: Database) extends UserRepository.Service {
  override def getUser: Task[String] =
    Task { s"USER! from ${database.connectionString}" }
}

trait Database {
  def connectionString: String
}

@Singleton
class DatabaseImpl @Inject() extends Database {
  override def connectionString: String = "localhost:5432"
}
