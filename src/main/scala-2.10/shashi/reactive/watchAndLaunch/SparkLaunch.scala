package shashi.reactive.watchAndLaunch

import org.apache.spark.launcher.SparkLauncher
import shashi.reactive.triggers.SparkJob
import org.apache.log4j._

/**
 * Created by shashidharsreddy on 2/19/16.
 */
object SparkLaunch {




  def launch(job:SparkJob): Unit = {

      val log=Logger.getLogger(this.getClass)

    val spark = new SparkLauncher()

    spark.setAppName(job.name).setSparkHome(job.sparkHome)
         .setAppResource(job.jarPath)
    .setMainClass(job.mainClass)
    .setMaster(job.masterHost)
    .launch()

    log.info("Spark Job has been submitted .....")

  }

}
