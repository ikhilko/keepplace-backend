resolvers ++= Seq(
  "gseitz@github" at "http://gseitz.github.com/maven/"
)

resolvers += Resolver.sonatypeRepo("releases")

resolvers += Resolver.sonatypeRepo("public")

resolvers += Resolver.url("Akka Snapshots", url("http://repo.akka.io/snapshots/"))

addSbtPlugin("com.typesafe.akka" % "akka-sbt-plugin" % "2.2.1")

addSbtPlugin("net.virtual-void" % "sbt-dependency-graph" % "0.7.4")

addSbtPlugin("com.typesafe.sbt" % "sbt-native-packager" % "1.0.5-M3")

addSbtPlugin("org.scalastyle" %% "scalastyle-sbt-plugin" % "0.6.0")

addSbtPlugin("org.scoverage" % "sbt-scoverage" % "1.3.3")

addSbtPlugin("org.scoverage" % "sbt-coveralls" % "1.0.0")
