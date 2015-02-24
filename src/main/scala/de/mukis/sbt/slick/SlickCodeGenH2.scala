package de.mukis.sbt.slick

import sbt._
import SlickCodeGenPlugin.autoImport._

/**
 * Generate Tables from H2
 */
object SlickCodeGenH2 extends AutoPlugin {

  override def requires = SlickCodeGenPlugin

  override lazy val projectSettings = Seq[Setting[_]](
    slickDriver := "scala.slick.driver.H2Driver",
    slickJDBCDriver := "org.h2.Driver",
    slickUrl := { db =>
      s"jdbc:h2:mem${db.map(":" + _) getOrElse ""}"
    }
  )
}