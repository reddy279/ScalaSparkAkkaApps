package shashi.reactive.watchAndLaunch

import java.nio.file._
import java.nio.file.attribute.BasicFileAttributes

import akka.actor.ActorRef
import org.apache.log4j.Logger
import org.apache.spark.Logging
import java.nio.file.StandardWatchEventKinds._
import shashi.reactive.actors.FileEventActor.{Deleted, Created}

import scala.collection.JavaConversions._


/**
 * WatchServiceTask takes an actor reference and notify the parent actor with the corresponding event that matched
 * @param notifyActor
 */


class WatchServiceTask(notifyActor: ActorRef) extends Runnable with Logging {

  val logger = Logger.getLogger(this.getClass)
  private val watchService = FileSystems.getDefault.newWatchService()


  def watchRecursively(root: Path): Unit = {
    watch(root)
    Files.walkFileTree(root, new SimpleFileVisitor[Path] {
      override def preVisitDirectory(dir: Path, attr: BasicFileAttributes) = {
        watch(dir)
        FileVisitResult.CONTINUE
      }
    })

  }

  def watch(path: Path) = path.register(watchService, ENTRY_CREATE, ENTRY_DELETE)

  def run(): Unit = {

    try {

      while (!Thread.currentThread().isInterrupted) {
        val key = watchService.take()
        key.pollEvents().foreach {
          event =>

            val relativePath = event.context().asInstanceOf[Path]
            val path = key.watchable().asInstanceOf[Path].resolve(relativePath)
            event.kind() match {
              case ENTRY_CREATE =>
                if (path.toFile.isDirectory) {
                  watchRecursively(path)
                }
                notifyActor ! Created(path.toString)

              case ENTRY_DELETE =>
                notifyActor ! Deleted(path.toString)

              case _ =>
                logger.info("Unregistered Event......")

            }

        }
        key.reset()
      }

    }
    catch {

      case e: InterruptedException =>
        logger.info("Events Thread got Interrupted")
    }
    finally {
      watchService.close()

    }


  }


}
