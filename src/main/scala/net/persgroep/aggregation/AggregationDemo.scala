//package net.persgroep.aggregation
//
//import org.apache.flink.api.common.functions.AggregateFunction
//import org.apache.flink.api.common.serialization.SimpleStringSchema
//import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment, createTypeInformation}
//import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows
//import org.apache.flink.streaming.api.windowing.time.Time
//import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer
//
//import java.util.Properties
//
//object AggregationDemo {
//
//    def main(args: Array[String]) : Unit = {
//
//        val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
//
//        // Setup Kafka consumer properties
//        val prop = new Properties()
//
//        val bootstrapServerTest = "b-1.profile-service-test.9kbfw2.c3.kafka.eu-west-1.amazonaws.com:9092,b-2.profile-service-test.9kbfw2.c3.kafka.eu-west-1.amazonaws.com:9092,b-3.profile-service-test.9kbfw2.c3.kafka.eu-west-1.amazonaws.com:9092"
//        val bootstrapServerProd = "b-1.profile-service-prod.n0vfm2.c3.kafka.eu-west-1.amazonaws.com:9092,b-2.profile-service-prod.n0vfm2.c3.kafka.eu-west-1.amazonaws.com:9092,b-3.profile-service-prod.n0vfm2.c3.kafka.eu-west-1.amazonaws.com:9092"
//        val bootstrapServerLocal = "localhost:9092"
//
//
//        prop.setProperty("bootstrap.servers", bootstrapServerLocal)
//        prop.setProperty("group.id", "local")
//
//        val topic = "input-kafka-topic"//"snowplow-events"
//        val deserializationSchema = InputDeserializationSchema
//        val kafkaConsumer = new FlinkKafkaConsumer[String](topic, new SimpleStringSchema(), prop)
//
//        val eventStream : DataStream[String] = env.addSource(kafkaConsumer)
//
//        val filteredStream = eventStream
//          .keyBy(e => e)
//          .window(TumblingEventTimeWindows.of(Time.seconds(10)))
//          .aggregate(MyAggregateFunction)
//
//
//
//
//
//        filteredStream.print()
//
//        env.setParallelism(1)
//        env.execute("Aggregation Demo Job")
//
//    }
//
//
//    object MyAggregateFunction extends AggregateFunction[String, List[OutputRecord], OutputRecord]{
//
//
//        // I want my accumulator to hold a list of OutputRecord for the time window
//        // So creating an empty list accumulator here
//        override def createAccumulator(): List[OutputRecord] = List[OutputRecord]()
//
//        // The add method will take in a InputRecord and add it to the empty list accumulator for the first time
//        // From the second time, it will just append InputRecord to the list accumulator
//        override def add(value: String, accumulator: List[OutputRecord]): List[OutputRecord] = {
//
//            println("Aggregator Add")
//            // Extract the page url from the InputRecord
//            // and create a new instance of OutputRecord with a view of 1
//            val newOutputRecord : OutputRecord = OutputRecord(value, 1)
//
//            // Prepend the above OutputRecord to the accumulator
//            newOutputRecord :: accumulator
//
//        }
//
//        override def getResult(accumulator: List[OutputRecord]): List[OutputRecord] = {
//
//            println("Aggregator getResult")
//
//            return accumulator
//
//            // Group the OutputRecord by page and count the distinct pages
////            return List(accumulator)
//
//
//        }
//
//        override def merge(a: List[OutputRecord], b: List[OutputRecord]): List[OutputRecord] = {
//
//            // Merge two accumulators to get one accumulator
//            // Don't know why is it done, still need to figure it out
//            println("Aggregator Merge")
//
//            a ::: b
//
//
//        }
//    }
//
//}
