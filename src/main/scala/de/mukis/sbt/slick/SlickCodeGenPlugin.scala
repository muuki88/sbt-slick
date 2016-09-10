package de.mukis.sbt.slick

import sbt._
import sbt.Keys._

sealed trait CallForm {
  def apply(driver: String,
            jdbc: String,
            url: String,
            outputDir: File,
            rootPackage: String,
            user: Option[String],
            pass: Option[String]): Array[String]
}

object CallForm {
  object WithoutCredentials extends CallForm {
    override def apply(driver: String,
                       jdbc: String,
                       url: String,
                       outputDir: File,
                       rootPackage: String,
                       user: Option[String],
                       pass: Option[String]) = Array(driver, jdbc, url, outputDir.getAbsolutePath, rootPackage)
  }

  object WithCredentials extends CallForm {
    override def apply(driver: String,
                       jdbc: String,
                       url: String,
                       outputDir: File,
                       rootPackage: String,
                       user: Option[String],
                       pass: Option[String]) = Array(
      driver, jdbc, url, outputDir.getAbsolutePath, rootPackage,
      user getOrElse sys.error("No user supplied"),
      pass getOrElse sys.error("No password supplied"))
  }
}

/**
 * Slick Code Generation Plugin
 */
object SlickCodeGenPlugin extends AutoPlugin {

  object autoImport extends Keys
  import autoImport._

  /** The basic settings for various code generations. */
  override lazy val projectSettings = Seq[Setting[_]](
    slickUser := None,
    slickPassword := None,
    slickPort := -1,
    slickPackage := "models",
    slickDatabases := Nil,
    slickHostName := "localhost",
    slickMakeDbPackage := identity,
    slickOutputDir := (sourceManaged in Compile).value,
    slickGenTables := {
      val cp = (dependencyClasspath in Compile).value
      val r = (runner in Compile).value
      val log = streams.value.log

      // place generated files in sbt's managed sources folder
      val callForm = slickCallForm.value
      // val outputDir = (sourceManaged in Compile).value.getPath
      val outputDir = slickOutputDir.value
      val driver = slickDriver.value
      val jdbc = slickJdbcDriver.value
      val url = slickUrl.value
      val rootPackage = slickPackage.value
      val dbs = slickDatabases.value
      val user = slickUser.value
      val pass = slickPassword.value

      val files = if (dbs.isEmpty) {
        log.warn("Trying to run slick code generation on no database")
        val fname = (sourceManaged in Compile).value / rootPackage.replaceAll("\\.", "/") / "Tables.scala"
        if (!fname.exists) {
          toError(
            r.run(
              "slick.codegen.SourceCodeGenerator",
              cp.files,
              callForm(driver, jdbc, url(None), outputDir, rootPackage, user, pass),
              log)
          )
          log.success(s"Using $driver. Output in $outputDir with package $rootPackage")
        }
        Seq(fname)
      } else {
        val makePackage = slickMakeDbPackage.value
        dbs.map { database =>
          val dbPackage = s"$rootPackage.${makePackage(database)}"
          // figuring out the filename by replacing dots in package path with slashes
          val fname = (sourceManaged in Compile).value / dbPackage.replaceAll("\\.", "/") / "Tables.scala"
          if (!fname.exists) {
            toError(
              r.run(
                "slick.codegen.SourceCodeGenerator",
                cp.files,
                callForm(driver, jdbc, url(Some(database)), outputDir, dbPackage, user, pass),
                log)
            )
            log.success(s"Using $driver. Output in $outputDir with package $dbPackage")
          }
          fname
        }
      }

      files
    }
  )

  // TODO add the sourceGenerator task here?
  // sourceGenerators in Compile <+= slickGenTables
  // override lazy val globalSettings = Seq[Setting[_]]()
  // override lazy val buildSettings = Seq[Setting[_]]()

}
