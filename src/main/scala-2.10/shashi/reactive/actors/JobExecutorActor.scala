package shashi.reactive.actors

import akka.actor.{Actor, ActorLogging}
import org.apache.spark.launcher.SparkLauncher
import shashi.reactive.actors.JobExecutorActor.{ExcecuteJob}
import shashi.reactive.triggers.{SparkJob, Marketing}
import shashi.reactive.watchAndLaunch.SparkLaunch


case object JobExecutorActor{

  case class ExcecuteJob(maketing:Marketing)
}



class JobExecutorActor extends Actor with ActorLogging{


    override  def receive ={
      case ExcecuteJob(marketing:SparkJob) =>{
        log.info("Job Executor launching Spark Job...")
        SparkLaunch.launch(marketing)

      }
    }

}
