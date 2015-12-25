package tutorial.com.scala


/**
 * In Java you can mix static and non-static attributes and methods in a class and then you end up
 * with static and non-static parts interleaved and it is hard to tell at a quick glance what's static and what's not
 * ------------------------------------------------------------------
 * In Scala, everything that's located inside the companion object is clearly not part of the
 * corresponding class's runtime objects, but is available from a static context
 *
 * In Case , if it is written inside a class, it is available to instances of that class, but not from a static context.
 */


class Companion {

  def clsMethod(): Unit = {
    println("Hye i am a Class Method")
  }
}

object Companion {

  def objMethod(): Unit = {
    println("Hey i am an Object Method")
  }
}


object testCompanion {

  def main(args: Array[String]) {
    val comClass: Companion = new Companion

    comClass.clsMethod()

    Companion.objMethod()


  }

}


/**
     Output
  --------------
  *   Hye i am a Class Method
  *  Hey i am Object Method
 */

