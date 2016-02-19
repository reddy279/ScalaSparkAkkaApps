package akka.com.scala

import java.nio.file._
import java.nio.file.StandardWatchEventKinds._
import java.nio.file.attribute.BasicFileAttributes
import org.apache.log4j.Logger
import akka.actor.ActorRef
import collection.JavaConversions._


class FSEventTask(notifyActor: ActorRef) extends Runnable {


  val logger = Logger.getLogger(this.getClass)
  /**
   * Initiating new Watch Service Event
   */
  private val watchService = FileSystems.getDefault.newWatchService()

  /**
   * Method looks into directories recursively for any event polls
   * @param root
   */
  def watchDirRecursively(root: Path): Unit = {
    watch(root)

    Files.walkFileTree(root, new SimpleFileVisitor[Path] {
      override def preVisitDirectory(dir: Path, attrs: BasicFileAttributes) = {
        watch(dir)
        FileVisitResult.CONTINUE
      }
    }
    )
  }

  /**
   * Registers the file located by this path with a watch service.
   *
   * @param path
   * @return
   */
  def watch(path: Path) =
    path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE, ENTRY_MODIFY)


  def run(): Unit = {
    try {

      logger.info(" Waiting for the File Sysetm Event......")
      while (!Thread.currentThread().isInterrupted) {

        val key = watchService.take()

        val pollevents = key.pollEvents()

        pollevents foreach {
          event =>

            val relativePath: Path = event.context().asInstanceOf[Path]
            val path: Path = key.watchable().asInstanceOf[Path].resolve(relativePath)

            event.kind() match {

              case ENTRY_CREATE => if (path.toFile.isDirectory) {
                watchDirRecursively(path)
                logger.info("New file has been created and thus notify the actor about it.")
                println( "New file has been created and thus notify the actor about it.")
                notifyActor ! FileCreationNotificationEvent(relativePath.toString)

              }

//                logger.info("New file has been created and thus notify the actor about it.")
//                println( "New file has been created and thus notify the actor about it.")
//                notifyActor ! FileCreationNotificationEvent(relativePath.toString)

              case ENTRY_DELETE =>
                notifyActor ! FileDeletionEvent(path.toFile)

              case ENTRY_MODIFY =>
                notifyActor ! FSMoniteringEvent(path)

              case x =>
                logger.info(s" Unknown Event $x")
            }

        }

        key.reset()
      }
    } catch {
      case e: InterruptedException => logger.info("Interrupting ........")
    } finally {

    }
    watchService.close()
  }
}

