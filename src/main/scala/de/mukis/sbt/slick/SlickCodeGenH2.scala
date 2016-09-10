package de.mukis.sbt.slick

import sbt._
import SlickCodeGenPlugin.autoImport._
import sbt.Keys._

/**
 * Generate Tables from H2
 */
object SlickCodeGenH2 extends AutoPlugin {

  override def requires = SlickCodeGenPlugin

  override lazy val projectSettings = Seq[Setting[_]](
    slickDriver := "slick.driver.H2Driver",
    slickJdbcDriver := "org.h2.Driver",
    slickUrl := { db =>
      s"jdbc:h2:mem${db.map(":" + _) getOrElse ""}"
    },
    slickCallForm := CallForm.WithoutCredentials
  )
}
