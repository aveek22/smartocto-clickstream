package net.persgroep.basic

/*
* This is a basic streaming program that reads string values from a kafka topic
* and writes to another kafka topic. Also it performs the following transformation.
* 
*   1. Splits the input string with spaces
*   2. Adds a "__*" at the end of the splitted string
*   3. Converts the strings to lowercase
*
* To compile simply run: sbt clean compile
* To run locally: sbt run
* 
* Sample Input: AM I doing It Correct
* Sample Output:
*  
*       am__*
*       i__*
*       doing__*
*       it__*
*       correct__*
* 
* 
* The program will start execution locally, need to open the kafka console producer and consumer on 
* two different terminals to view the outputs.
* 
*/

import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.flink.streaming.api.scala.{StreamExecutionEnvironment, _}
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer

import java.util.Properties
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer

object StreamToLowerCase {

  def main(args: Array[String]): Unit = {
    
    // Create the flink execution environment
    val flinkEnv: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
    flinkEnv.setParallelism(1)


    // Setup Kafka consumer properties
    val kafkaConsumerProperties = new Properties()
    kafkaConsumerProperties.setProperty("bootstrap.servers", "localhost:9092")
    kafkaConsumerProperties.setProperty("group.id", "local")

    val sourceTopic = "input-kafka-topic"
    val deserializationSchema = new SimpleStringSchema()
    val kafkaConsumer = new FlinkKafkaConsumer[String](sourceTopic, deserializationSchema, kafkaConsumerProperties)


    // Setup Kafka producer properties
    val kafkaProducerProperties = new Properties()
    kafkaProducerProperties.setProperty("bootstrap.servers", "localhost:9092")

    val destinationTopic = "output-kafka-topic"
    val serializationSchema = new SimpleStringSchema()
    val kafkaProducer = new FlinkKafkaProducer[String](destinationTopic, serializationSchema, kafkaProducerProperties)


    // Prepare the event stream
    val eventStream : DataStream[String] = flinkEnv.addSource(kafkaConsumer)

    // inputEventStream.flatMap {str => str.split(" ")}
    

    // Convert input strings to lower case
    // eventStream.flatMap {str => str.split(" ")}
    
    eventStream
      .flatMap {s => s.split(" ")}
      .map {s => s.toLowerCase()}
      .map {s => s + "__*"}
      .addSink(kafkaProducer)
    
    // eventStream.map {s => s + "__*"}

    
    //Prepare the output stream
    // eventStream.addSink(kafkaProducer)


    // Execute the flink program.
    flinkEnv.execute("Local Flink Job")

  }

}
