# Slick Code Generation Plugin

This plugin allows you to easily generate slick Table schemas.
Take a look at the `test-project` for example configurations.

# Getting started

Add the plugin in your `plugins.sbt`

```scala
addSbtPlugin("de.mukis" % "sbt-slick" % "0.1")
```

Enable a database of your choice an configure it.

## build.sbt

Make sure to add slick and the slickTask to the sourceGenerators

```scala
libraryDependencies ++= Seq(
   "com.typesafe.slick" %% "slick" % "2.1.0",
   "com.typesafe.slick" %% "slick-codegen" % "2.1.0"
)

sourceGenerators in Compile <+= slickGenTables
```

You can change the root package for your code with `slickPackage`.

```scala
slickPackage := "models" // default
```

## H2

For the very simple in memory solution with a provided _import.sql_

```scala
libraryDependencies += "com.h2database" % "h2" % "1.3.170"

enablePlugins(SlickCodeGenH2)

// slickUrl is a function from databaseName:Option[String] => url:String
slickUrl := { _ => s"jdbc:h2:mem:test;INIT=runscript from '${baseDirectory.value / "h2.create.sql"}'" }
```

Or you can just add the databases and take care of the creation yourself


```scala
enablePlugins(SlickCodeGenH2)
slickDatabases ++= Seq("user", "cities")
```

## MySql

```scala
libraryDependencies += "mysql" % "mysql-connector-java" % "5.1.34"

enablePlugins(SlickCodeGenMySql)
slickUser := Some("test")
slickPassword := Some("test")
slickDatabases += "your-db"
```

This will generate the mysql tables.

