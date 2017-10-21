[![Build Status](https://travis-ci.org/muuki88/sbt-slick.svg?branch=master)](https://travis-ci.org/muuki88/sbt-slick)

# NOT MAINTAINED

Thanks for your interest in this plugin. The sbt-plugin is not maintained anymore because
the code necessary to generated slick models is so little a plugin doesn't make any sense.

See the [slick codegen example code](https://github.com/slick/slick-codegen-example) on how
to generate slick models.

# Slick Code Generation Plugin

This plugin allows you to easily generate slick Table schemas.
Take a look at the `test-project` for example configurations.

# Getting started

Add the plugin in your `plugins.sbt`

With sbt 0.13.6+

```scala
resolvers += Resolver.bintrayRepo("muuki88", "sbt-plugins")
```

For older versions use

```scala
resolvers += Resolver.url(
   "mukis-sbt-plugin-releases",
   url("http://dl.bintray.com/muuki88/sbt-plugins/"))(Resolver.ivyStylePatterns)

```

### Slick 2.x

```
addSbtPlugin("de.mukis" % "sbt-slick" % "0.3")
```

### Slick 3.x

```
addSbtPlugin("de.mukis" % "sbt-slick" % "0.4")
```


Enable a database of your choice an configure it.

## build.sbt

Make sure to add slick and the slickTask to the sourceGenerators

```scala
val slickVersion = "x.y.z"
libraryDependencies ++= Seq(
   "com.typesafe.slick" %% "slick" % slickVersion,
   "com.typesafe.slick" %% "slick-codegen" % slickVersion
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
