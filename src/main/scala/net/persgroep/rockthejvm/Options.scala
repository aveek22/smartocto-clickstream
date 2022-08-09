package net.persgroep.rockthejvm

object Options {


    val aList : List[Int] = List(1,2 ,3)
    val bList : List[Char] = List('a', 'b')

    val aTransformedList: List[Int] = aList.map(x => x * 2)
    val aTransformedList2 : List[Int] = aList.flatMap(x => List( x , x * 3))


    // The next section describes how a for comprehensions
    // This code structure is available in the cleaning app.
    val combinedLists: List[String] = for {
        num <- List(1,2,3)
        char <- List('a','b')
    } yield s"$num-$char"

    // The above method is same and can be re-written as follows.
    val combinedListsV2 : List[String] = aList.flatMap(num => bList.map(char => s"$num-$char"))


    // Now let's talk about Options in details
    // Suppose a function returns string or null
    def unsafeMethod(arg: Int) : String = null

    val potentialValue: String = unsafeMethod(44)
    val potentialValue2: String = unsafeMethod(67)

    //if we need to write defensive code, i.e. code to check if there are no nulls
    val combinedResult: String =
        if (potentialValue == null)
            if(potentialValue2 == null)
                "ERROR1"
            else
                "ERROR2"
        else
            if (potentialValue2 == null)
                "ERROR3"
            else
                potentialValue.toUpperCase() + potentialValue2.toUpperCase()


    // This entire block can be re-written in Scala as follows
    def betterCombinedResult: Option[String] = for {
        potVal <- Option(unsafeMethod(44))
        potVal2 <- Option(unsafeMethod(67))
    } yield potVal.toUpperCase() + potVal2.toUpperCase()

    val finalPotVal: String = betterCombinedResult.getOrElse("ERROR")
    // default is ERROR otherwise print betterCombinedResult

    // How to read the above code block
    /*
    * Considering there is a value in the first option, then let that be potVal
    * Considering there is a value in the second option, then let that be potVal2
    * If both the values exist, then return the combining result
    *
    * The return type is going to be Some()
    * If either one or both values are null, then it will return None
    *
    *
    * */








    def main(args: Array[String]) : Unit = {

        println(s"Using the FOR comprehension: " + combinedLists.toString())
        println(s"Using the FlatMap and Map function: " + combinedListsV2.toString())
        println(combinedLists == combinedListsV2)

    }

}
