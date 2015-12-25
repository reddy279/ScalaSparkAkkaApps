package tutorial.com.scala

/**
 * fold takes data in one format and gives it back to you in another.
 * All three methods—fold, foldLeft, and foldRight—do the same thing
 */



  object Fold_Samples_Simple {


    def foldSample(): Int = {

      val numbers = List(5, 4, 8, 6, 2)
      numbers.fold(0) {
        (z, i) => z + i
      }
    }







    def main(args: Array[String]) {
      println(foldSample()) // 25
    }
  }

