package tutorial.com.scala

/**
 * The case classes are special classes that are used in pattern matching with case expressions.
 *
 * First, the compiler automatically converts the constructor arguments into immutable fields (vals).
 * The val keyword is optional. If you want mutable fields, use the var keyword.
 * So, our constructor argument lists are now shorter.
 *
 * Second, the compiler automatically implements equals, hashCode, and toString methods to the class,
 * which use the fields specified as constructor arguments.
 * So, we no longer need our own toString methods.
 */


case class Person(val firstName: String, val lastName: String, val age: Int)

object caseClass {
  def main(args: Array[String]) {
    val person1 = Person("Raju", "Sharma", 25)
    val person2 = Person("Krishna", "Babu", 35)
    val person3 = Person("Jhonson", "King", 45)


    val persons: List[Person] = List(person1, person2, person3)

    for (person <- persons) {
      person match {

        case Person("Raju", "Sharma", 25) => println("Hey This is Raju ")
        case Person("Krishna", "Babu", 35) => println("Hey this is Krishna")
        case Person("Jhonson", "King", 45) => println("Hye This is Jhonson")
        case _ => println(" Iam not any body")
      }

    }
  }
}

/*
Hey This is Raju
Hey this is Krishna
Hye This is Jhonson

 */

