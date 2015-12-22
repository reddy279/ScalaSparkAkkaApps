package com.scala.spark.util.test

import com.scala.spark.util.ScalaUtilities
import org.apache.hadoop.fs
import org.apache.hadoop.fs.FileSystem
import org.scalatest.{BeforeAndAfter, Matchers, FlatSpec}

class ScalaUtilitiesTest extends FlatSpec with Matchers with BeforeAndAfter{

  before {

  }
  after {

  }

  /**
   *  A test case to test the file system connection and file existance
   */

   "The following method" should  " return File System object "  in {
      val returnObject:Boolean = ScalaUtilities.isFileSystemFileExist("hdfs://localhost:9000/user/shashidharsreddy/RampUp/CurrSmallCSV.csv")
     returnObject shouldBe  true
   }
}
