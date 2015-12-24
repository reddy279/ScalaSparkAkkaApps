package com.scala.spark.metastore.util.test


import com.scala.spark.util.metastore.{MetaStore, JsonExtractor}
import org.scalatest.{BeforeAndAfter, FlatSpec, Matchers}



class MetaStoreTest extends FlatSpec with Matchers with BeforeAndAfter {

  before{

  }
  after {

  }


  "A json Reader " should " be able to load the Json file " in {
    val json:MetaStore=JsonExtractor.loadJsonFile[MetaStore]("src/test/resources/config")
    json.metastore("employee").file_location shouldBe ("/tmp/shashi")
    json.metastore("customer").file_format(0).fieldName shouldBe(Some("cust_name"))
  }


}
