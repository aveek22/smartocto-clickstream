package net.persgroep.basic

object FoldLeft extends App {

    println("FoldLeft operation...")

    val pageviewList = List[PageView](PageView("homepage","home",1),PageView("homepage","home",1),PageView("aboutpage","about",1))

    val groupedList = pageviewList.groupBy(e => e.page)
    println("Grouped List: " + groupedList)

    val newGroupedList = pageviewList.groupBy(e => List(e.page, e.uri))
    println("New Grouped List: " + newGroupedList)

    val mappedList = newGroupedList.map(e => e._2)
    println("Mapped List: " + mappedList)

    val foldedList = newGroupedList.map(e => e._2
      .foldLeft(PageView(e._1.head, e._1(1),0)){
          (acc, curr) => PageView(acc.page, acc.uri, (acc.view + curr.view))
      })
    println(foldedList)

    val newMap = Map(
        "City" -> "Dibrugarh",
        "State" -> "Kolkata"
    )
    println("New Map: " + newMap)

    val planets =
        List(("Mercury", 57.9), ("Venus", 108.2), ("Earth", 149.6),
            ("Mars", 227.9), ("Jupiter", 778.3))
    planets.foreach {
        case ("Earth", distance) =>
            println(s"Our planet is $distance million kilometers from the sun")
        case _ =>
    }

    val numPairs = List((2, 5), (3, -7), (20, 56))
    for ((a, b) <- numPairs) {
        println(a * b)
    }









    case class PageView (
                        page: String,
                        uri: String,
                        view: Int
                        )

}
