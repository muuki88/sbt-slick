name := "sbt-slick"
organization := "de.mukis"
licenses += ("Apache-2.0", url("http://www.apache.org/licenses/LICENSE-2.0"))

scalaVersion in Global := "2.10.6"
sbtPlugin := true

git.remoteRepo := "git@github.com:muuki88/sbt-slick.git"

// testing
scriptedSettings
scriptedLaunchOpts <+= version apply { v => "-Dproject.version="+v }


releasePublishArtifactsAction := PgpKeys.publishSigned.value
publishMavenStyle := false
bintrayOrganization := None
bintrayRepository := "sbt-plugins"
licenses += ("Apache-2.0", url("https://www.apache.org/licenses/LICENSE-2.0.html"))
