package shashi.reactive.actors

import akka.actor.{Props, Actor, ActorLogging}
import shashi.reactive.actors.MarketingManagerActor.AllEventsRegistered
import shashi.reactive.triggers.Marketing


case object MarketingManagerActor {

  def props(marketing:Marketing)=Props(new MarketingManagerActor(marketing))
  case object AllEventsRegistered

}




class MarketingManagerActor(marketing:Marketing) extends Actor with ActorLogging{



   val eventMarketingManager = context.actorOf(EventManagerActor.props(marketing.events))
   val jobExecutor =context.actorOf(Props[JobExecutorActor])


  override def receive :Receive = {
  case AllEventsRegistered =>
      log.info("ALL the required evets are registered !!, now the Time for Job Run")
      jobExecutor ! JobExecutorActor.ExcecuteJob(marketing)
  }

}
