package com.scala.spark.metasote.util

import com.scala.spark.util.ScalaUtilities
import org.apache.log4j.Logger
import org.json4s.DefaultFormats


object JsonExtractor {

   implicit  val format = DefaultFormats

  val logger = Logger.getLogger(this.getClass)

  def loadJsonFile[T](file_path:String)(implicit  m: Manifest[T]):T = {

    logger.debug("Loading and Extracting Json Files.")

    val json_string:String= ScalaUtilities.getFSContent(file_path)
    val json_file=org.json4s.native.JsonMethods.parse(json_string)
     json_file.extract[T]

  }


}
