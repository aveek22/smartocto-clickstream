name := "smartocto-learning"

version := "0.1-SNAPSHOT"

organization := "net.persgroep"

ThisBuild / scalaVersion := "2.12.16"

val flinkVersion = "1.13.6"
val snowplowVersion = "2.1.0"

val flinkDependencies = Seq(
    "org.apache.flink" %% "flink-clients" % flinkVersion,
    "org.apache.flink" %% "flink-scala" % flinkVersion,
    "org.apache.flink" %% "flink-streaming-scala" % flinkVersion,
    "org.apache.flink" %% "flink-connector-kafka" % flinkVersion,
    "com.snowplowanalytics" %% "snowplow-scala-analytics-sdk" % snowplowVersion)




val circeVersion = "0.14.1"

lazy val circeDependencies = Seq(
  "io.circe" %% "circe-core" % circeVersion,
  "io.circe" %% "circe-generic" % circeVersion,
  "io.circe" %% "circe-parser" % circeVersion
)

lazy val testDependencies = Seq(
//  "org.scalatest" %% "scalatest" % "3.2.7",
  "org.scalatest" %% "scalatest" % "3.2.7" % "test"
)




lazy val root = (project in file(".")).
  settings(
    libraryDependencies ++= flinkDependencies,
    libraryDependencies ++= circeDependencies,
    libraryDependencies ++= testDependencies,
    // assembly / mainClass := Some("net.persgroep.basic.BasicStreaming"),
    // assembly / mainClass := Some("net.persgroep.basic.StreamToLowerCase"),
    // assembly / mainClass := Some("net.persgroep.basic.BasicTumblingWindow"),
    assembly / mainClass := Some("net.persgroep.jsonparser.JsonParser"),
  )

// assembly / mainClass := Some("net.persgroep.basic.BasicStreaming")

// make run command include the provided dependencies
// Compile / run  := Defaults.runTask(Compile / fullClasspath,
//                                    Compile / run / mainRunner,
//                                    Compile / run / runner
//                                   ).evaluated

// // stays inside the sbt console when we press "ctrl-c" while a Flink programme executes with "run" or "runMain"
// Compile / run / fork := true
// Global / cancelable := true

// exclude Scala library from assembly
// assembly / assemblyOption  := (assembly / assemblyOption).value.copy(includeScala = false)
