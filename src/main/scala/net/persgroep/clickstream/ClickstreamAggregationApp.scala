package net.persgroep.clickstream


import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.flink.streaming.api.scala.{StreamExecutionEnvironment, _}
import org.apache.flink.streaming.connectors.kafka.{FlinkKafkaConsumer,FlinkKafkaProducer}

import java.util.Properties

import org.apache.flink.api.common.functions.FlatMapFunction
import org.apache.flink.streaming.api.windowing.assigners.TumblingProcessingTimeWindows
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.api.common.time

import net.persgroep.clickstream.models.SourceEvent
import net.persgroep.clickstream.models.TargetAggregation

object ClickstreamAggregationApp {

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
        .map {(_ , 2)}
        .keyBy(_._1)
        .window(TumblingProcessingTimeWindows.of(Time.seconds(10)))
        .sum(1)
        .map { x => x.toString()}       // need to convert it to string because Kafka producer is of type string
        .addSink(kafkaProducer)


    // Execute the flink program.
    flinkEnv.execute("Local Flink Job")

  }
  
}
