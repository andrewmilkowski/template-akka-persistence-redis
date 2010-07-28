import sbt._

class TestAkkaProject(info: ProjectInfo) extends DefaultWebProject(info) {

  // Versions
  
  lazy val LIFT_VERSION  = "2.0-scala280-SNAPSHOT"
  lazy val AKKA_VERSION  = "0.10"
  lazy val MULTIVERSE_VERSION = "0.6-SNAPSHOT"
  lazy val SCALATEST_VERSION  = "1.2-for-scala-2.8.0.final-SNAPSHOT"
  lazy val JUNIT_VERSION  = "4.8.2"
  lazy val SLF4J_VERSION  = "1.6.1"
  lazy val VERKZ_VERSION = "2.2.1"

  // Compiler settings

  override def compileOptions = super.compileOptions ++
    Seq("-deprecation",
        "-Xmigration",
        "-Xcheckinit",
        "-Xwarninit",
        "-encoding", "utf8")
        .map(x => CompileOption(x))
  override def javaCompileOptions = JavaCompileOption("-Xlint:unchecked") :: super.javaCompileOptions.toList
 

  // Repositories

  lazy val embeddedRepo = "Local Maven Repository" at (info.projectPath / "embedded-repo").asURL.toString
  lazy val mavenLocal = "Local Maven Repository" at "file://" + Path.userHome + "/.m2/repository"
  lazy val mavenRemote = "Remote Maven Repository" at "http://repo1.maven.org/maven2"
  lazy val mavenCodehouse = "Codehouse Maven Repository" at "http://snapshots.repository.codehaus.org"
  lazy val mavenScalableSolutions = "Akka Maven Repository" at "http://scalablesolutions.se/akka/repository"
  lazy val mavenJboss = "Jboss Maven Repository" at "https://repository.jboss.org/nexus/content/groups/public"



  // Dependencies (Compile)

  // Akka

  lazy val akkaCore = "se.scalablesolutions" % "akka-core_2.8.0"  % AKKA_VERSION
  lazy val akkaPersistenceCommon = "se.scalablesolutions" % "akka-persistence-common_2.8.0"  % AKKA_VERSION
  lazy val akkaPersistenceRedis = "se.scalablesolutions" % "akka-persistence-redis_2.8.0"  % AKKA_VERSION

  // Lift
  lazy val liftCommon = "net.liftweb" % "lift-common" % LIFT_VERSION % "compile->default"
  lazy val liftJson = "net.liftweb" % "lift-base" % LIFT_VERSION % "compile->default"
  lazy val liftUtil = "net.liftweb" % "lift-util" % LIFT_VERSION % "compile->default"

  
  lazy val sjson = "sjson.sjson" % "sjson_2.8.0" % "0.7-SNAPSHOT" % "compile->default"
  lazy val configgy = "net.lag" % "configgy_2.8.0" % "1.5.4" % "compile->default"
  lazy val multiverse = "org.multiverse" % "multiverse-alpha" % MULTIVERSE_VERSION % "compile->default"
  lazy val jsr166x = "jsr166x" % "jsr166x" % "1.0" % "compile->default" 
  lazy val werkz = "org.codehaus.aspectwerkz" % "aspectwerkz-nodeps-jdk5" % VERKZ_VERSION % "compile->default"
  lazy val werkz_core = "org.codehaus.aspectwerkz" % "aspectwerkz-jdk5" % VERKZ_VERSION % "compile->default"
  lazy val netty = "org.jboss.netty" % "netty" % "3.2.1.Final" % "compile"
  lazy val jta_1_1 = "org.apache.geronimo.specs" % "geronimo-jta_1.1_spec" % "1.1.1" % "compile->default"
  lazy val paranamer = "com.thoughtworks.paranamer" % "paranamer" % "2.0" % "compile->default"

  // Dependencies (Test)

  lazy val junit = "junit" % "junit" % JUNIT_VERSION % "test->default"
  lazy val slf4j = "org.slf4j" % "slf4j-api" % SLF4J_VERSION % "test->default"
  lazy val specs = "org.scala-tools.testing" %% "specs" % "1.6.5" % "test->default"
  lazy val scalaTest = "org.scalatest" % "scalatest" % SCALATEST_VERSION % "test->default"
  lazy val scalacheck = "org.scala-tools.testing" %% "scalacheck" % "1.7" % "test->default"
  lazy val mockito = "org.mockito" % "mockito-all" % "1.8.4" % "test->default"

}

