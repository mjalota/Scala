
name := "instrumentprices"

version := "1.0"

scalaVersion := "2.12.1"

libraryDependencies += "com.typesafe.akka" % "akka-actor_2.11" % "2.4.16"
libraryDependencies += "com.typesafe" % "config" % "1.2.1"
libraryDependencies += "com.typesafe.scala-logging" % "scala-logging-slf4j_2.10" % "2.1.2"

//libraryDependencies += "org.scalatest" % "scalatest_2.11" % "3.2.0-SNAP2"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.0" % "test"
libraryDependencies += "org.mockito" % "mockito-all" % "1.10.19"