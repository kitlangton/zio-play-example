name := """zio-play-example"""
organization := "com.example"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.3"

val zioVersion    = "1.0.3"
val doobieVersion = "0.9.2"

val doobie: Seq[ModuleID] = Seq(
  "org.tpolecat" %% "doobie-core"     % doobieVersion,
  "org.tpolecat" %% "doobie-free"     % doobieVersion,
  "org.tpolecat" %% "doobie-postgres" % doobieVersion,
  "org.tpolecat" %% "doobie-hikari"   % doobieVersion
)

libraryDependencies += guice
libraryDependencies ++= Seq(
  "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0"    % Test,
  "dev.zio"                %% "zio"                % zioVersion,
  "dev.zio"                %% "zio-streams"        % zioVersion,
  "dev.zio"                %% "zio-macros"         % zioVersion,
  "dev.zio"                %% "zio-interop-cats"   % "2.2.0.1",
  "dev.zio"                %% "zio-test"           % zioVersion % "test",
  "dev.zio"                %% "zio-test-sbt"       % zioVersion % "test"
) ++ doobie

testFrameworks += new TestFramework("zio.test.sbt.ZTestFramework")
