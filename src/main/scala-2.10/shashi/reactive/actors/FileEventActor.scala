package shashi.reactive.actors

import java.nio.file.FileSystems

import akka.actor.{ActorLogging, Props, Actor}
import shashi.reactive.actors.FileEventActor.{Deleted, Created}
import shashi.reactive.triggers.FileEvent
import shashi.reactive.watchAndLaunch.WatchServiceTask

/**
 * Created by shashidharsreddy on 2/18/16.
 */

case object FileEventActor {

  case class Created(path: String)

  case class Deleted(path: String)

 def props(event: FileEvent) = Props(new FileEventActor(event:FileEvent))

}


class FileEventActor(event:FileEvent) extends Actor with ActorLogging{


  val watchServiceTask= new WatchServiceTask(self)
  val watchServiceThread=new Thread(watchServiceTask,"watchService")


  override def preStart(): Unit ={
    watchServiceThread.setDaemon(true)
    watchServiceThread.start()
    watchServiceTask.watchRecursively(FileSystems.getDefault.getPath(event.filePath))
  }

  override def postStop(): Unit ={
    watchServiceThread.interrupt()

  }
  override def receive ={
    case Created(path)=>
      log.info("New File is Created")
      context.parent ! EventManagerActor.EventTriggered(event)
    case Deleted(path) =>
      log.info("File is Deleted")
  }

}
