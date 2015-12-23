package com.scala.spark.util.test

import com.scala.spark.util.ScalaUtilities
import org.scalatest.{BeforeAndAfter, FlatSpec, Matchers}

class ScalaUtilitiesTest extends FlatSpec with Matchers with BeforeAndAfter{

  before {

  }
  after {

  }

  /**
   *  A test case to test the HDFS file system connection and file existance
   */

   "The following method" should  " check if the HDFS File System file exists "  in {
      val returnObject:Boolean = ScalaUtilities.isFileSystemFileExist("hdfs://localhost:9000/user/shashidharsreddy/RampUp/CurrSmallCSV.csv","hdfs")
     returnObject shouldBe  true
   }

  /**
   *  A test case to test the AWS S3 file system connection and file existance
   */

  "The following method" should  " check if the AWS S3 File System file exists "  in {
    val returnObject:Boolean = ScalaUtilities.isFileSystemFileExist("s3://shashi-s3/data/shashi.txt","s3")
    returnObject shouldBe  true
  }
}
