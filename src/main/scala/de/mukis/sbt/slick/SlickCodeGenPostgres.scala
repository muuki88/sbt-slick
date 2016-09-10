package de.mukis.sbt.slick

import de.mukis.sbt.slick.SlickCodeGenPlugin.autoImport._
import sbt._
import sbt.Keys._

/**
 * Generate Tables from MySql
 */
object SlickCodeGenPostgres extends AutoPlugin {

  override def requires = SlickCodeGenPlugin

  override lazy val projectSettings = Seq[Setting[_]](
    slickDriver := "slick.driver.PostgresDriver",
    slickJdbcDriver := "org.postgresql.Driver",
    slickPort := 5432,
    slickUrl := { database =>
      val db = database getOrElse ""
      val hostName = slickHostName.value
      val port = slickPort.value
      s"jdbc:postgresql://$hostName:$port/$db"
    },
    slickCallForm := CallForm.WithCredentials
  )
}
