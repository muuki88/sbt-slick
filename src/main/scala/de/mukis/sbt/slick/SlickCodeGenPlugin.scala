package de.mukis.sbt.slick

import sbt._
import sbt.Keys._

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
    slickMakeDbPackage := identity,
    slickGenTables := {
      val cp = (dependencyClasspath in Compile).value
      val r = (runner in Compile).value
      val log = streams.value.log

      // place generated files in sbt's managed sources folder
      val outputDir = (sourceManaged in Compile).value.getPath
      val driver = slickDriver.value
      val jdbc = slickJDBCDriver.value
      val url = slickUrl.value
      val rootPackage = slickPackage.value
      val dbs = slickDatabases.value

      val files = if (dbs.isEmpty) {
        log.warn("Trying to run slick code generation on no database")
        val fname = (sourceManaged in Compile).value / rootPackage.replaceAll("\\.", "/") / "Tables.scala"
        if (!fname.exists) {
          toError(
            r.run("scala.slick.codegen.SourceCodeGenerator", cp.files, Array(
              driver, jdbc, url(None), outputDir, rootPackage
            ), log)
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
              r.run("scala.slick.codegen.SourceCodeGenerator", cp.files, Array(
                driver, jdbc, url(Some(database)), outputDir, dbPackage
              ), log)
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