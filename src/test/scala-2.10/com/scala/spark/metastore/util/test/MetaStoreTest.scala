package com.scala.spark.metastore.util.test

import com.scala.spark.metasote.util.JsonExtractor
import org.scalatest.{BeforeAndAfter, FlatSpec, Matchers}
import com.scala.spark.metasote.util._


class MetaStoreTest extends FlatSpec with Matchers with BeforeAndAfter {

  before{

  }
  after {

  }


  "A json Reader " should " be able to load the Json file " in {

    val json=JsonExtractor.loadJsonFile[MetaStore]("src/resources/config/metastore.json")


  }


}
