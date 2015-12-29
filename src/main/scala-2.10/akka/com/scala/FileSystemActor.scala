package akka.com.scala

import akka.actor.Actor
import akka.event.LoggingReceive
import org.apache.log4j.Logger


class FileSystemActor extends Actor {


  val logger = Logger.getLogger(this.getClass)
  /**
   * The 'self' field holds the ActorRef for this actor.
   * Can be used to send messages to itself
   * self ! message
   */
  val fSystemTask = new FSEventTask(self)
  val watchThread = new Thread(fSystemTask, "fSystemTaskService")

  /**
   * Is called when an Actor is started.
   * Actors are automatically started asynchronously when created.
   */
  override def preStart(): Unit = {
    logger.info("File System Actor Pre Start Function...")
    println("File System Actor Pre Start Function...")
    watchThread.setDaemon(true)
    watchThread.start()
  }

  /**
   * Is called asynchronously after 'actor.stop()' is invoked.
   * Empty default implementation.
   */
  override def postStop(): Unit = {
    watchThread.interrupt()
  }

  /**
   * This defines the initial actor behavior, it must return a partial function
   * with the actor logic.
   *
   * Wrap a Receive partial function in a logging enclosure, which sends a
   * debug message to the event bus each time before a message is matched.
   * This includes messages which are not handled.
   *
   * <pre><code>
   * def receive = LoggingReceive {
   * case x => ...
   * }
   * </code></pre>
   */

  def receive: Actor.Receive = LoggingReceive {

    case FSMoniteringEvent(path) => fSystemTask watchDirRecursively path
      logger.info("in FileSystemActor --> Receive ")
      println("in FileSystemActor --> Receive ")
    case FileCreationNotificationEvent(path) => {
      path match {
        case "xyz" => {
          logger.info("File got changed ... do action here")
          println("File got changed ... do action here")
        }
        case _ => logger.info(" Unknown fil got changed.....No action planned on this...")
      }
    }

    case FileDeletionEvent(fileOrDir) => {
      logger.info("Some file or Directory got deleted.....no action planned ")
      println("Some file or Directory got deleted.....no action planned ")
    }

  }

}
