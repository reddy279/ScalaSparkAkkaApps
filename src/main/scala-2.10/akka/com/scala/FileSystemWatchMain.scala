package akka.com.scala

import java.nio.file.FileSystems
import java.util.concurrent.TimeUnit

import akka.actor.{Props, ActorSystem}


object FileSystemWatchMain extends App {

  val system = ActorSystem("WatchFileSystem")

  system.log.info(" Watch Dog has been started ")

  val fsActor = system.actorOf(Props[FileSystemActor], "fileSystemActor")

  fsActor ! FileCreationEvent(FileSystems.getDefault().getPath("/Users/shashidharsreddy/Akka-Test/").toFile)

  fsActor ! FileCreationNotificationEvent(FileSystems.getDefault().getPath("/Users/shashidharsreddy/Akka-Test/").toString)

  fsActor ! FSMoniteringEvent(FileSystems.getDefault().getPath("/Users/shashidharsreddy/Akka-Test/"))

  TimeUnit.SECONDS.sleep(3)
  //system.shutdown()

}
