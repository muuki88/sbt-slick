enablePlugins(SlickCodeGenH2)

name := "simple-test"

version := "0.1.0"

libraryDependencies ++= List(
  "com.typesafe.slick" %% "slick" % "3.0.0",
  "com.typesafe.slick" %% "slick-codegen" % "3.0.0",
  "com.h2database" % "h2" % "1.3.170"
)

slickUrl := { _ => s"jdbc:h2:mem:test;INIT=runscript from '${baseDirectory.value / "create.sql"}'" }

sourceGenerators in Compile <+= slickGenTables
