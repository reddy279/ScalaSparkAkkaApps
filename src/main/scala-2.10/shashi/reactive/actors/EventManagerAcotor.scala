package shashi.reactive.actors

import akka.actor.{PoisonPill, Props, Actor, ActorLogging}
import shashi.reactive.actors.EventManagerActor
import shashi.reactive.actors.EventManagerActor.EventTriggered
import shashi.reactive.triggers.{FileEvent, Event}

import scala.collection.immutable.HashMap

/**
 * Created by shashidharsreddy on 2/19/16.
 */


case object EventManagerActor {

  def props(events:List[_ <: Event])= Props(new EventManagerAcotor(events))
  case class EventTriggered(event:Event)
}


class EventManagerAcotor(events:List[_<:Event]) extends Actor with ActorLogging{

  var eventTriggered= new HashMap[Event, Boolean]

  override def preStart(): Unit ={
    events.foreach(event =>{
      if (event.isInstanceOf[FileEvent]){
        log.info("Creating Child Event Watchers")
        val fileEve= event.asInstanceOf[FileEvent]
        eventTriggered += (fileEve->false)
        context.actorOf(FileEventActor.props(fileEve))
      }
    })

  }

  def allEventsTriggered():Boolean={
    eventTriggered.forall(p=>{
      p._2==true
    })
  }

  override def receive:Receive={
    case EventTriggered(event) =>
      log.info("Events have been triggered")
      eventTriggered +=(event->true)
      sender ! PoisonPill
      if(allEventsTriggered()){
        log.info("All events have been triggeres")
        context.parent ! MarketingManagerActor.AllEventsRegistered
      }
  }

}
