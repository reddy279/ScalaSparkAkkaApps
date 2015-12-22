package com.scala.spark.util

import java.net.URI


import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{Path, FileSystem}
import org.apache.log4j.Logger


object ScalaUtilities {

val logger = Logger.getLogger(this.getClass)

  /**
   * This function renturns FileSystem Object
   * @return
   */

def getFileSystemObject(file_location:String): FileSystem ={

  val configuration:Configuration =new Configuration()
    logger.info("File Syste, Location " + file_location)
  FileSystem.get(new URI(file_location), configuration)
}

  /**
   * This method verifies whether the given file exists or not
   * @param file_location
   * @return
   */

 def isFileSystemFileExist(file_location:String):Boolean ={
    val fileSystem:FileSystem  = getFileSystemObject(file_location)
    val path:Path = new Path(file_location)
     fileSystem.exists(path)
 }
}
