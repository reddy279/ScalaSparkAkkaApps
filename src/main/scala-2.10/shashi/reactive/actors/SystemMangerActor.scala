package shashi.reactive.actors

import java.util

import akka.actor.{PoisonPill, ActorRef, ActorLogging, Actor}
import shashi.reactive.actors.SystemMangerActor.{StopMarkting, AddMarketing}
import shashi.reactive.triggers.Marketing

import scala.collection.immutable.HashMap

/**
 * Created by shashidharsreddy on 2/18/16.
 */

object SystemMangerActor {

  case class AddMarketing(marketing:Marketing)
  case class StopMarkting(marketingName:String)
}



class SystemMangerActor  extends  Actor with ActorLogging{

      var currentRunninMarketings= new HashMap[String, ActorRef]



   override def receive: Receive = {


     case AddMarketing(marketing) => {
       log.info(s"Adding new Marketing  ->${marketing.marketingName}")

       val marketingActor= context.actorOf(MarketingManagerActor.props(marketing))
       currentRunninMarketings +=(marketing.marketingName ->marketingActor)

     }




     case StopMarkting(marketingName:String)=>{
      var ref = currentRunninMarketings.get(marketingName) match {

      case Some(actor:ActorRef) => actor ! PoisonPill
      case None => log.info(s"$marketingName is not running in the system")
     }
     }

   }



}
