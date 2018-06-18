organization in ThisBuild := "$organization$"
version in ThisBuild := "$version$"

// the Scala version that will be used for cross-compiled libraries
scalaVersion in ThisBuild := "2.12.4"

val macwire = "com.softwaremill.macwire" %% "macros" % "2.3.0" % "provided"
val scalaTest = "org.scalatest" %% "scalatest" % "3.0.4" % Test

lazy val `$name;format="norm"$` = (project in file("."))
  .aggregate(`$name;format="norm"$-api`, `$name;format="norm"$-impl`)

lazy val crossType = CrossType.Full


lazy val `$name;format="norm"$-api-root` = (crossProject in file("$name;format="norm"$-api"))
  .settings(
    name := "gheo-provider-api-root",
    EclipseKeys.eclipseOutput := Some("eclipse_target"),
    unmanagedSourceDirectories in Compile := Seq((scalaSource in Compile).value) ++ crossType.sharedSrcDir(baseDirectory.value, "main"),
    unmanagedSourceDirectories in Test := Seq((scalaSource in Test).value) ++ crossType.sharedSrcDir(baseDirectory.value, "test")
  )
lazy val `$name;format="norm"$-api` = 
  `$name;format="norm"$-api-root`.jvm
  .settings(
    name := "$name;format="norm"$-api",
    libraryDependencies ++= Seq(
      lagomScaladslApi
    )
  )
lazy val `$name;format="norm"$-api-js` = 
  `$name;format="norm"$-api-root`.js
  .settings(
    name := "$name;format="norm"$-api-js",
    libraryDependencies ++= Seq(
      
    )
  )

lazy val `$name;format="norm"$-impl` = (project in file("$name;format="norm"$-impl"))
  .enablePlugins(LagomScala)
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslPersistenceCassandra,
      lagomScaladslKafkaBroker,
      lagomScaladslTestKit,
      macwire,
      scalaTest
    )
  )
  .settings(lagomForkedTestSettings: _*)
  .dependsOn(`$name;format="norm"$-api`)
