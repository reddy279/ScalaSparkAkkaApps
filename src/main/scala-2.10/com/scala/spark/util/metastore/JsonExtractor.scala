package com.scala.spark.util.metastore

import java.util.Properties

import com.scala.spark.util.ScalaUtilities
import org.apache.commons.lang.text.StrSubstitutor
import org.apache.log4j.Logger
import org.json4s.DefaultFormats


object JsonExtractor {
  implicit val format = DefaultFormats

  val logger = Logger.getLogger(this.getClass)

  /**
   *  This loads the Json file substitute all env properties before parsing
   * @param file_path
   * @param m
   * @tparam T
   * @return
   */

  def loadJsonFile[T](file_path: String)(implicit m: Manifest[T]): T = {

    logger.debug("Loading and Extracting Json Files.")

    println("File Path :" + file_path)

    val json_string = ScalaUtilities.getFSContent(file_path+"/"+ "metastore.json")

    val envStream = ScalaUtilities.getInputStream(file_path + "/env.properties")
    val properties: Properties = new Properties
    properties.load(envStream)

    val strSub:StrSubstitutor = new StrSubstitutor(properties)
     val json_lines = strSub.replace(json_string)

     println("Substituted Json :"+json_lines)

    val json_file = org.json4s.native.JsonMethods.parse(json_lines.toString)
    json_file.extract[T]

  }


}
