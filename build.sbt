name := "dms2"
organization := "com.mandrcon"
scalaOrganization := "org.typelevel"
scalaVersion := "2.12.3-bin-typelevel-4"
scalacOptions += "-Ypartial-unification"

libraryDependencies ++= Seq(
  "org.typelevel" %% "cats-core" % "1.0.0-MF",
  "com.chuusai" %% "shapeless" % "2.3.2",

  "org.sangria-graphql" %% "sangria" % "1.2.2",
  "org.sangria-graphql" %% "sangria-relay" % "1.2.2",
  "org.sangria-graphql" %% "sangria-json4s-jackson" % "1.0.0",
  "com.twitter" %% "finatra-http" % "2.13.0",

  "org.tpolecat" %% "doobie-core"      % "0.5.0-M8",
  "org.tpolecat" %% "doobie-postgres"  % "0.5.0-M8",
)
