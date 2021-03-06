name := """Digitary"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava, PlayEbean)

scalaVersion := "2.12.2"

libraryDependencies += guice
//libraryDependencies += javaJpa
libraryDependencies ++= Seq(
  javaJpa,
  "org.hibernate" % "hibernate-entitymanager" % "5.1.0.Final" // replace by your jpa implementation
)
// Test Database
libraryDependencies += "com.h2database" % "h2" % "1.4.194"
libraryDependencies += javaWs % "test"



// Testing libraries for dealing with CompletionStage...
libraryDependencies += "org.assertj" % "assertj-core" % "3.6.2" % Test
libraryDependencies += "org.awaitility" % "awaitility" % "2.0.0" % Test
libraryDependencies += "org.mockito" % "mockito-core" % "2.1.0" % Test

// Make verbose tests
testOptions in Test := Seq(Tests.Argument(TestFrameworks.JUnit, "-a", "-v"))
