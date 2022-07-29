package net.persgroep.jsonparser

import io.circe._, io.circe.parser._
import java.nio.file.{Files, Paths}
import java.io.File

object JsonParser extends App{

    val inputJsonFile = "src/test/resources/sample-json.json"
    // val jsonString = Files.readString(Paths.get(inputJsonFile))

    val json = Files.readAllLines(Paths.get(inputJsonFile))

    val parseResult : Either[ParsingFailure, Json] = parse(json.toString())

    println(json)
    println(json.toString().stripMargin)
    println(parseResult)
  
}
