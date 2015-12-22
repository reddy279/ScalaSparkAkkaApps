logLevel := Level.Warn

resolvers += Resolver.url("artifactory" , url("http://scalasbt.artifactoryonline.com/scalasbt/sbt-plugin-releases"))(Resolver.ivyStylePatterns)
addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.14.0")
addSbtPlugin("com.typesafe.sbt" % "sbt-native-packager" % "1.0.3")