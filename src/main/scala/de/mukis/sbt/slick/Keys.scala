package de.mukis.sbt.slick

import sbt._

/**
 * Keys for slick code generation
 */
trait Keys {

  /**
   * database name => url
   */
  type MakeUrl = Option[String] => String

  /**
   * database name => package name
   */
  type MakePackage = String => String

  val slickDriver = SettingKey[String]("slick-driver", "The slick driver, e.g. 'scala.slick.driver.MySQLDriver'")
  slickOutputDir = SettingKey[File]("slick-output-dir", "The directory (excluding package) to write the classes to, e.g. 'file(src/main)' or (sourceManaged in Compile).value.getPath")
  val slickJdbcDriver = SettingKey[String]("slick-jdbc-driver", "The jdbc driver, e.g. 'com.mysql.jdbc.Driver'")
  val slickUrl = TaskKey[MakeUrl]("slick-url", "URL to database, e.g. 'jdbc:mysql://localhost:3306/your-db'")
  val slickCallForm = SettingKey[CallForm]("slick-call-form", "The calling form to SourceCodeGenerator, with or without credentials included")
  val slickUser = SettingKey[Option[String]]("slick-user", "User to access database")
  val slickPassword = SettingKey[Option[String]]("slick-password", "Password to access database")
  val slickHostName = SettingKey[String]("slick-host-name", "Database server host name, defaults to 'localhost'")
  val slickPort = TaskKey[Int]("slick-port", "Port to access database")
  val slickDatabases = SettingKey[Seq[String]]("slick-databases", "databases to generate tables for")
  val slickPackage = SettingKey[String]("slick-package", "Output package for Tables.scala")
  val slickMakeDbPackage = TaskKey[MakePackage]("slick-db-package", "Call for each database to generate the package name")
  val slickGenTables = TaskKey[Seq[File]]("slick-gen-tables", "Task to generate the table")
}
