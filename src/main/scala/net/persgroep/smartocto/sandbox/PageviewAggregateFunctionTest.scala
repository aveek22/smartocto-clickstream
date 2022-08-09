package net.persgroep.smartocto.sandbox

object PageviewAggregateFunctionTest extends App{

    println("Pageview Aggregation App...")

    val pageviewRecords : List[PageViewRecord] = List[PageViewRecord] (
        PageViewRecord("page_view","/home",1),
        PageViewRecord("page_view","/home",1),
        PageViewRecord("click","/about",1),
//        PageViewRecord("page_view","/about",1),
//        PageViewRecord("page_view","/contacts",1),
//        PageViewRecord("page_view","/home",1),
//        PageViewRecord("page_view","/about",1)
    )

    val groupByRecords = pageviewRecords
      .groupBy(e => List(e.eventType, e.uri))

    /*  Output:
            Map(
                List(page_view, /home) -> List(PageViewRecord(page_view,/home,1), PageViewRecord(page_view,/home,1)),
                List(click, /about) -> List(PageViewRecord(click,/about,1))
            )
     */


    val mappedRecords = groupByRecords
      .map(e => e)
//      .foldLeft(PageViewRecord(e._1.head,e._1(1),0)))
//        .foldLeft(PageViewRecord(e._1.head, e._1(1), 0))
//        ((acc, curr) => PageViewRecord(acc.eventType, acc.uri, acc.views + curr.views)))
//      .toList

    println(mappedRecords)




    case class PageViewRecord(
                             eventType: String,
                             uri: String,
                             views: Int
                             )

}
