enablePlugins(SlickCodeGenH2)

name := "simple-test"

version := "0.1.0"

libraryDependencies ++= List(
   "com.typesafe.slick" %% "slick" % "2.1.0",
   "com.typesafe.slick" %% "slick-codegen" % "2.1.0",
   "com.h2database" % "h2" % "1.3.170"
)

sourceGenerators in Compile <+= slickGenTables