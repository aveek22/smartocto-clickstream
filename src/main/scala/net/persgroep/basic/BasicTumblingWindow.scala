package net.persgroep.basic

/*
* This is a basic streaming program that reads string values from a kafka topic
* and writes to another kafka topic. It takes 10 seconds as a tumbling window 
* and counts the number of words in that specific window.
*
* To compile simply run: sbt clean compile
* To run locally: sbt run
* 
* 
* The program will start execution locally, need to open the kafka console producer and consumer on 
* two different terminals to view the outputs.
* 
*/

import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.flink.streaming.api.scala.{StreamExecutionEnvironment, _}
import org.apache.flink.streaming.connectors.kafka.{FlinkKafkaConsumer,FlinkKafkaProducer}

import java.util.Properties

import net.persgroep.model.WordWithCount
import org.apache.flink.api.common.functions.FlatMapFunction
import org.apache.flink.streaming.api.windowing.assigners.TumblingProcessingTimeWindows
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.api.common.time

object BasicTumblingWindow {

  def main(args: Array[String]): Unit = {
    
    // Create the flink execution environment
    val flinkEnv: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
    // flinkEnv.setParallelism(1)


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

    val cleanedStream = eventStream
        .flatMap {_.split(" ")}
        .map {(_ , 1)}
        .keyBy(_._1)
        .window(TumblingProcessingTimeWindows.of(Time.seconds(10)))
        .sum(1)
        .map { x => x.toString()}       // need to convert it to string because Kafka producer is of type string
        .addSink(kafkaProducer)


    // Execute the flink program.
    flinkEnv.execute("Local Flink Job")

  }

}
