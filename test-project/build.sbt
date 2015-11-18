name := "test-project"
version := "0.1.0"
libraryDependencies ++= Seq(
   "com.typesafe.slick" %% "slick" % "3.1.0",
   "com.typesafe.slick" %% "slick-codegen" % "3.1.0",
   "com.h2database" % "h2" % "1.3.170",
   "mysql" % "mysql-connector-java" % "5.1.34"
)

// h2
enablePlugins(SlickCodeGenH2)
slickUrl := { _ => s"jdbc:h2:mem:test;INIT=runscript from '${baseDirectory.value / "h2.create.sql"}'" }

// mysql
enablePlugins(SlickCodeGenMySql)
//slickPort := 33066
//slickUser := Some("test")
//slickPassword := Some("test")
//slickDatabases += "your-db"

sourceGenerators in Compile <+= slickGenTables
