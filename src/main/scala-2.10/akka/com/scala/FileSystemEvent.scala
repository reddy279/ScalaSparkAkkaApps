package akka.com.scala

import java.io.File
import java.nio.file.Path


/**
 * Defined all the events that are performed under this actor service.
 */
sealed trait FileSystemEvent

  case class FileCreationEvent(fileOrDir:File) extends FileSystemEvent
  case class FileCreationNotificationEvent(fileName:String) extends FileSystemEvent
  case class FileDeletionEvent(fileOrDir:File) extends FileSystemEvent
  case class FSMoniteringEvent (path:Path)



