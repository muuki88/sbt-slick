lazy val root = Project("plugins", file(".")) dependsOn(slickCodeGenPlugin)

lazy val slickCodeGenPlugin = file("..").getAbsoluteFile.toURI
