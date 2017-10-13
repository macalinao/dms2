name := "dms2"
organization := "com.mandrcon"
scalaOrganization := "org.typelevel"
scalaVersion := "2.12.3-bin-typelevel-4"
scalacOptions += "-Ypartial-unification"

libraryDependencies ++= Seq(
  "org.typelevel" %% "cats-core" % "1.0.0-MF",
  "org.sangria-graphql" %% "sangria" % "1.2.2",
  "org.sangria-graphql" %% "sangria-relay" % "1.2.2",
  "org.sangria-graphql" %% "sangria-json4s-jackson" % "1.0.0",
  "com.twitter" %% "finatra-http" % "2.9.0",
)
