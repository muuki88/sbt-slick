import bintray.Keys._
import sbtrelease._

name := "sbt-slick"
organization := "de.mukis"
licenses += ("Apache-2.0", url("http://www.apache.org/licenses/LICENSE-2.0"))

scalaVersion in Global := "2.10.4"
sbtPlugin := true

git.remoteRepo := "git@github.com:muuki88/sbt-slick.git"

// testing
scriptedSettings
scriptedLaunchOpts <+= version apply { v => "-Dproject.version="+v }

// publishing
releaseSettings
bintraySettings

ReleaseKeys.publishArtifactsAction := PgpKeys.publishSigned.value
publishMavenStyle := false
repository in bintray := "sbt-plugins"
licenses += ("Apache-2.0", url("https://www.apache.org/licenses/LICENSE-2.0.html"))
bintrayOrganization in bintray := None
