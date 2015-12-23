package com.scala.spark.util

import java.net.URI

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FileSystem, Path}
import org.apache.log4j.Logger


object ScalaUtilities {

  val logger = Logger.getLogger(this.getClass)

  /**
   * This function renturns FileSystem Object
   * @return
   */

  def getFileSystem(file_location: String, file_system_type: String): FileSystem = {

    val configuration: Configuration = new Configuration()
    var file_system: FileSystem = null
    if (file_system_type.equalsIgnoreCase("HDFS")) {
      logger.info("HDFS File System, Location " + file_location)
      file_system = FileSystem.get(new URI(file_location), configuration)
    }
    if (file_system_type.equalsIgnoreCase("S3")) {
      logger.info("S3 File System, Location " + file_location)
      file_system = getAWSFileSystem(file_location)
    }
    file_system
  }


  /**
   * This Function returns the Amazon AWS S3 Files System object
   */
  def getAWSFileSystem(file_location: String): FileSystem = {

    val configuration: Configuration = new Configuration()
    try {
      logger.info("Reading AWS Environment Variabls from system")
      println("Reading AWS Environment Variabls from system")
      val awsAccessKeyId: String = sys.env("AWS_ACCESS_KEY_ID") //"AKIAJ3YUCQDPWH5CXEUQ"
      val awsSecretAccessKey: String = sys.env("AWS_SECRET_ACCESS_KEY") //"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"
      println("awsAccessKeyId= " + awsAccessKeyId + "\n " + "awsSecretAccessKey= " + awsSecretAccessKey)

      if (awsAccessKeyId != null && awsSecretAccessKey != null) {
        logger.info("Setting the AWS keys to Configuration object")
        configuration.set("fs.s3n.awsAccessKeyId", awsAccessKeyId)
        configuration.set("fs.s3n.awsSecretAccessKey", awsSecretAccessKey)
        configuration.set("fs.s3a.awsAccessKeyId", awsAccessKeyId)
        configuration.set("fs.s3a.awsSecretAccessKey", awsSecretAccessKey)
        configuration.set("fs.s3.awsAccessKeyId", awsAccessKeyId)
        configuration.set("fs.s3.awsSecretAccessKey", awsSecretAccessKey)
      }
    } catch {
      case e: Exception => logger.info("AWS keys were not found...")
    }
    configuration.set("fs.s3.impl", "org.apache.hadoop.fs.s3native.NativeS3FileSystem")
    configuration.set("fs.s3a.impl", "org.apache.hadoop.fs.s3a.S3AFileSystem")
    configuration.set("fs.s3a.server-side-encryption-algorithm", "AES256")

    FileSystem.get(new URI(file_location), configuration)


  }


  /**
   * This method verifies whether the given file exists or not
   * path must be either your hdfs location  ex:hdfs://localhost:9000/.... or S3 location
   * @param file_location
   * @return
   */

  def isFileSystemFileExist(file_location: String, file_system_type: String): Boolean = {
    val fileSystem: FileSystem = getFileSystem(file_location, file_system_type)
    val path: Path = new Path(file_location)
    fileSystem.exists(path)
  }


}
