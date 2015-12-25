package tutorial.com.scala


class Fold_Sample_Detailed(val name: String, val age: Int, val sex: Symbol)


/** Below Companion Object act as Factory to Instantiate the Class at run time  */

object Fold_Sample_Detailed {

  def apply(name: String, age: Int, sex: Symbol) = new Fold_Sample_Detailed(name, age, sex)
}


object foledLeft_implementer {

  def left_folder(): List[String] = {

    val personList: List[Fold_Sample_Detailed] = List(Fold_Sample_Detailed("Arland", 67, 'male),
      Fold_Sample_Detailed("Smitha", 45, 'female),
      Fold_Sample_Detailed("James", 35, 'male))

    val stringList = personList.foldRight(List[String]()) { (f, z) =>
      val title = f.sex match {
        case 'male => "Mr."
        case 'female => "Ms."
      }

      z :+ s"$title ${f.name}, ${f.age}"
    }


//    val stringList = personList.foldLeft(List[String]()) { (z, f) =>
//      val title = f.sex match {
//        case 'male => "Mr."
//        case 'female => "Ms."
//      }
//
//      z :+ s"$title ${f.name}, ${f.age}"
//    }

    stringList
  }

  def main(args: Array[String]) {
    val output: List[String] = left_folder()
    println(output(0)) // Mr.James, 35
  }
}




