package tutorial.com.scala


/**
 * Companion Objects are great for encapsulating things like factory methods.
 * Instead of having to have Vehicle and Honda everywhere, you can have a class with a companion object take on the factory responsibilities.
 */

  abstract class VehicleCounter {

    var vehicles = 0

    def name: String

    def count(): Unit = {

      vehicles += 1
      println("%d %s count ".format(vehicles, name))
    }

  }


  abstract class Vehicle {

    def calling: VehicleCounter

    calling.count()
  }


  object Honda extends VehicleCounter {

    val name = " Hero Honda "
  }


  class Honda extends Vehicle {

    def calling = Honda
  }

  object Toyota extends VehicleCounter {

    val name = " Toyota Camry "
  }


  class Toyota extends Vehicle {

    def calling = Toyota
  }




object testComapanion_detailed {

  def  main (args: Array[String]) {
    val honda:Honda =new Honda

    val camry:Toyota=new Toyota

  }




}