package com.scala.spark.util.sparkApp

import org.apache.log4j.Logger
import org.apache.spark.{SparkConf, SparkContext}

/**
 * An Object which contains Spark utilities
 */
object SparkUtils {

  val logger = Logger.getLogger(this.getClass)


  /**
   * Returns Spark context object which enables the encryption and compresses the output format.
   * @param appName
   * @param env
   * @return
   */

  def getSparkContext(appName:String, env:String="production"): SparkContext ={


    logger.debug("configuring Spark Context......")

    val conf:SparkConf = new SparkConf().setAppName(appName)
    if(env != "production"){

      conf.setMaster("local[4]")

    }

    logger.debug(" Enabling encryption for accessing S3 file system")

    conf.set("spark.hadoop.fs.s3a.server-side-encryption-algorithm", "AES256")

    val context:SparkContext= SparkContext.getOrCreate(conf)

    val hadoopConf = context.hadoopConfiguration
    hadoopConf.set("mapreduce.output.fileoutputformat.compress", "true")
    hadoopConf.set("mapreduce.output.fileoutputformat.compress.codec","org.apache.hadoop.io.compress.GzipCodec")
    context

  }

}
