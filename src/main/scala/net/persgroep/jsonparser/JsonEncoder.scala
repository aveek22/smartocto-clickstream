package net.persgroep.jsonparser

import io.circe._
import io.circe.syntax._
import java.util.UUID


object JsonEncoder extends App{

    val aveek = Author("Aveek", "Data Engineer")
    val article1 = Article(UUID.randomUUID(), "SQL Shack", "How to learn Scala", aveek)

    // Creating an implicit authorEncoder that will return an Encoder of type Author
    // It can be defined as a simple function from author to a Json object

    implicit val authorEncoder: Encoder[Author] = author => Json.obj(
        "name" -> author.name.asJson,
        "bio" -> author.bio.asJson
    )

    implicit val articleEncoder: Encoder[Article] = article => Json.obj(
        "id" -> article.id.asJson,
        "title" -> article.title.asJson,
        "content" -> article.content.asJson,
        "author" -> article.author.asJson
    )

    val articleJson = article1.asJson.spaces4

    println(articleJson) 
}

case class Author(
    name: String,
    bio: String
)

case class Article(
    id : UUID,
    title: String,
    content: String,
    author : Author

)


