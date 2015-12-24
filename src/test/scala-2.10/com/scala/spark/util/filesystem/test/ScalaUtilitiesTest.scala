package com.scala.spark.util.test

import com.scala.spark.util.ScalaUtilities
import org.scalatest.{BeforeAndAfter, FlatSpec, Matchers}

class ScalaUtilitiesTest extends FlatSpec with Matchers with BeforeAndAfter {

  before {

  }
  after {

  }

  /**
   * A test case to test the HDFS file system connection and file existance
   */

  "The  method" should " check if the HDFS File System file exists " in {
    val returnObject: Boolean = ScalaUtilities.isFileSystemFileExist("hdfs://localhost:9000/user/shashidharsreddy/RampUp/CurrSmallCSV.csv", "hdfs")
    returnObject shouldBe true
  }

  /**
   * A test case to test the AWS S3 file system connection and file existance
   */

  "The  method" should " check if the AWS S3 File System file exists " in {
    val returnObject: Boolean = ScalaUtilities.isFileSystemFileExist("s3://shashi-s3/data/shashi.txt", "s3")
    returnObject shouldBe true
  }

  /**
   * Test case read the content of the Fils System files
   */

  " The method" should "read the content of the files" in {
    val content: String = ScalaUtilities.getFSContent("s3://shashi-s3/data/shashi.txt")
    content.contains("Suresh") should be(true)
  }

  /**
   * A test on read and Write to file
   */

  "The method " should "write the content to file " in {
    val list: List[String] = List("Shashi", "Chintu")

    ScalaUtilities.writeToFSFile("s3://shashi-s3/data/writeShashi.txt", list)
    val content: String = ScalaUtilities.getFSContent("s3://shashi-s3/data/writeShashi.txt")
    content.contains("Chintu") should be(true)
  }

  /**
   * A test to verify the dates
   */
  "The method " should "return yesterdays date" in {
    ScalaUtilities.getDate(-1) should be("20151222")
  }


}
