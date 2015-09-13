name := "NewsRecsDemo"

version := "1.0"

scalaVersion := "2.10.4"

libraryDependencies += "org.apache.lucene" % "lucene-core" % "4.0.0"

libraryDependencies += "org.apache.spark" %% "spark-core" % "1.4.1"

libraryDependencies += "org.apache.spark" %% "spark-mllib" %"1.4.1"

libraryDependencies += "org.mongodb" %% "casbah" % "2.7.0"

libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.3.10"

libraryDependencies += "org.specs2" %% "specs2-core" % "3.6.4" % "test"

