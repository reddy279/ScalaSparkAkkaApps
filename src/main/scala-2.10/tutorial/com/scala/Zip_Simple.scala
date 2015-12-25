package tutorial.com.scala

/**
 * The zip function combines two lists into tuples.If the lists are of differing lengths, the shorter length is used.
 *
 * The zipAll function will keep all elements, filling in specified values for the blanks
 **/


object Zip_Samples {

  def zipper() {
    val list_first: List[Int] = List(1, 2, 3)
    val list_second: List[String] = List("one", "two", "three")
    // Zip
    val tuple = list_first.zip(list_second)
    println(tuple) // output --> List((1,one), (2,two), (3,three))
    println(tuple.indices(2)) //output --> 2
  }

  def zipIndex(): Unit = {
    val list_first: List[Int] = List(1, 2, 3)
    val list_second: List[String] = List("one", "two", "three")


    //Zip with Index
    val tuples: List[(String, Int)] = list_second.zipWithIndex
    println(tuples) //List((one,0), (two,1), (three,2))
    println(tuples.indices) // Range(0, 1, 2)

    for (tuple <- tuples) {

      println(tuple._1 + " " + tuple._2)
    }
  }


  def zipAll(): Unit = {
    val list_first: List[Int] = List(1, 2, 3, 4, 5)
    val list_second: List[String] = List("one", "two", "three")

    val zip_tuple = list_first.zip(list_second)
    println(zip_tuple) //List((1,one), (2,two), (3,three))
    val zipAll_tuple = list_first.zipAll(list_second, "missing", "ten")
    println(zipAll_tuple) //List((1,one), (2,two), (3,three), (4,ten), (5,ten))


  }


  def main(args: Array[String]) {
    zipper()
    zipIndex()
    zipAll()
  }
}








