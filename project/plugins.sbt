resolvers += "jgit-repo" at "http://download.eclipse.org/jgit/maven"
resolvers += Resolver.url("bintray-sbt-plugin-releases", url("http://dl.bintray.com/content/sbt/sbt-plugin-releases"))(
  Resolver.ivyStylePatterns
)


libraryDependencies <+= (sbtVersion) { sv =>
  "org.scala-sbt" % "scripted-plugin" % sv
}

addSbtPlugin("com.typesafe.sbt" % "sbt-git" % "0.6.4")
addSbtPlugin("com.github.gseitz" % "sbt-release" % "0.8.5")
addSbtPlugin("me.lessis" % "bintray-sbt" % "0.1.2")
addSbtPlugin("com.typesafe.sbt" % "sbt-pgp" % "0.8.3")
