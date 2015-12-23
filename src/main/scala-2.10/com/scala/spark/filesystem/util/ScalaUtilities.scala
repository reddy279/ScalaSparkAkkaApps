package com.scala.spark.util

import java.io.{OutputStreamWriter, BufferedWriter, InputStream, StringWriter}
import java.net.URI

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FileStatus, FileSystem, Path}
import org.apache.hadoop.io.compress.{CompressionCodecFactory, CompressionCodec}
import org.apache.log4j.Logger


object ScalaUtilities {

  val logger = Logger.getLogger(this.getClass)

  /**
   * This function renturns FileSystem Object
   * @return
   */

  def getFileSystem(file_location: String): FileSystem = {

    val configuration: Configuration = new Configuration()

    logger.info("HDFS File System, Location " + file_location)
    FileSystem.get(new URI(file_location), configuration)
  }


  /**
   * This Function returns the Amazon AWS S3 Files System object
   */
  def getAWSFileSystem(file_location: String): FileSystem = {

    val configuration: Configuration = new Configuration()
    try {
      logger.info("Reading AWS Environment Variabls from system")
      println("Reading AWS Environment Variabls from system")
      val awsAccessKeyId: String = sys.env("AWS_ACCESS_KEY_ID")
      val awsSecretAccessKey: String =sys.env("AWS_SECRET_ACCESS_KEY")
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
    var fileSystem: FileSystem = null

    if (file_system_type.equalsIgnoreCase("hdfs")) {
      fileSystem = getFileSystem(file_location)
    }
    if (file_system_type.equalsIgnoreCase("s3")) {
      fileSystem = getAWSFileSystem(file_location)
    }
    val path: Path = new Path(file_location)
    fileSystem.exists(path)
  }


  /**
   * Read the File system files content
   * it will also read the compressed files located in the file system
   *
   */

  def getFSContent(file_location: String): String = {

    logger.info("Read the file content from File System")

    var fSystem: FileSystem = null
    val stringWriter: StringWriter = new StringWriter()
    var output: String = null
    try {
      fSystem = getAWSFileSystem(file_location)
      val path: Path = new Path(file_location)
      logger.info("Using Compression Factory for files in compressed format")
      val codecFactory: CompressionCodecFactory = new CompressionCodecFactory(fSystem.getConf)


      val files: Array[FileStatus] = fSystem.listStatus(path)

      var inputStream: InputStream = null
      for (file <- files) {
        if (!file.getPath.getName.startsWith("_")) {

          val codec: CompressionCodec = codecFactory.getCodec(file.getPath)

          if (codec != null) {
            inputStream = codec.createInputStream(fSystem.open(file.getPath))
          } else {
            inputStream = fSystem.open(file.getPath)

          }

          org.apache.commons.io.IOUtils.copy(inputStream, stringWriter)
          //org.apache.commons.io.IOUtils.copy(inputStream, stringWriter,"UTF-8")
          output = stringWriter.toString
          println(output)
        }
      }
    } finally {
      if (fSystem != null)
        fSystem.close()
    }
    output
  }

  /**
   * This function is useful for reading files as it is .
   * @param file_location
   * @return
   */

  def getInputStream(file_location: String) = {
    var fileSystem: FileSystem = null
    var path: Path = null
    try {
      fileSystem = getAWSFileSystem(file_location)
      path = new Path(file_location)
      fileSystem.open(path)
    } finally {
      if (fileSystem != null) fileSystem.close()
    }
  }

  /**
   * Deletes the Directory from File System
   * @param folder_path
   * @return
   */
  def deleteFSDirectory(folder_path: String) = {
    logger.debug("delete File System Directory..")
    val configuration: Configuration = new Configuration
    val path: Path = new Path(folder_path)
    val fileSystem = FileSystem.get(new URI(folder_path), configuration)
    fileSystem.delete(path, true)
  }

  /**
   * Write content to files in File System
   * @param file_path
   * @param toData
   */

  def writeToFSFile(file_path: String, toData: List[String]): Unit = {

    val fileSystem: FileSystem = getAWSFileSystem(file_path)
    val path: Path = new Path(file_path)
    var buffwriter: BufferedWriter = null

    try {
      buffwriter = new BufferedWriter(new OutputStreamWriter(fileSystem.create(path, true)))

      for (data <- toData) {
        buffwriter.write(data)
        buffwriter.newLine()
      }
    }
    finally {
      buffwriter.close()
    }
  }






}
