package shashi.reactive.triggers



/**
 * Created by shashidharsreddy on 2/17/16.
 */


trait Event
trait Job


case class FileEvent(filePath:String) extends Event
case class StreamEvent(zookeeperUri:String,username:String,password:String) extends Event

case class SparkJob(name:String,sparkHome:String,jarPath:String,mainClass:String,masterHost:String) extends Job


case class Marketing (marketingName:String, events: List[Event], jobs: List[_ <: Job])


