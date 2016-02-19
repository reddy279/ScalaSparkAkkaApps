package shashi.reactive.Main

import java.io.File

import akka.actor
import akka.actor.{Props, ActorSystem, Actor, ActorLogging}

import com.scala.spark.util.ScalaUtilities
import com.typesafe.config.ConfigFactory
import shashi.reactive.actors.{SystemMangerActor, SystemMangerActor$, FileEventActor}

import shashi.reactive.triggers.{SparkJob, StreamEvent, FileEvent, Marketing}
import scala.collection.JavaConversions._
import org.json4s.ShortTypeHints
import org.json4s.native.Serialization
import org.json4s.native.Serialization.{read}


/**
 * Created by shashidharsreddy on 2/17/16.
 */
object Main extends App {

  implicit  val formats = Serialization.formats(ShortTypeHints(List(classOf[FileEvent],classOf[StreamEvent],classOf[SparkJob])))



   val config=ConfigFactory.load()
   val configList=config.getStringList("marketing")

  val actorSystem =ActorSystem("ShashiEvent")
  val actor =actorSystem.actorOf(Props[SystemMangerActor],"marketTest")
      // val actor = actorSystem.actorOf(FileEventActor.props("/Users/shashidharsreddy/ScalaSparkApps/src/main/resources"),"Test")

   configList.foreach(
      name=>{
         val path="src/main/resources/"+name
        //println("Path "+path)
         val cfg= ScalaUtilities.getFSContent(path)
       // println("config  " +cfg)
         val marketing=read[Marketing](cfg)
         //println("===="+marketing)
        actor ! SystemMangerActor.AddMarketing(marketing)

      })


}
