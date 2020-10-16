package models

import com.google.inject.AbstractModule
import play.api.{Configuration, Environment}
import zio.interop.play.ZioComponents

class Module(environment: Environment, configuration: Configuration) extends AbstractModule {
  override def configure(): Unit = {
    bind(classOf[Database]).to(classOf[DatabaseImpl])
    bind(classOf[ZioComponents]).asEagerSingleton()
  }

}
