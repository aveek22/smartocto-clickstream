package net.persgroep.smartocto

import com.snowplowanalytics.snowplow.analytics.scalasdk.Event
import net.persgroep.smartocto.models.{AggregatePageview, SnowplowRecord}
import net.persgroep.smartocto.serialization.SnowplowDeserializationSchema
import org.apache.flink.api.common.functions.{FilterFunction, MapFunction}
import org.apache.flink.api.scala.createTypeInformation
import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer

import java.awt
import java.util.Properties

object AggregationApp {

    def localCompute(): Unit = {

        val flinkEnv = StreamExecutionEnvironment.getExecutionEnvironment
        val kafkaEnv = "prod"       // prod/test

        val bootstrapServerTest = "b-1.profile-service-test.9kbfw2.c3.kafka.eu-west-1.amazonaws.com:9092,b-2.profile-service-test.9kbfw2.c3.kafka.eu-west-1.amazonaws.com:9092,b-3.profile-service-test.9kbfw2.c3.kafka.eu-west-1.amazonaws.com:9092"
        val bootstrapServerProd = "b-1.profile-service-prod.n0vfm2.c3.kafka.eu-west-1.amazonaws.com:9092,b-2.profile-service-prod.n0vfm2.c3.kafka.eu-west-1.amazonaws.com:9092,b-3.profile-service-prod.n0vfm2.c3.kafka.eu-west-1.amazonaws.com:9092"
        val groupId = "smartocto-qa-group"
        val topicNameTest = "snowplow-events-qa"
        val topicNameProd = "snowplow-events"

        val topicName       = if (kafkaEnv == "prod") topicNameProd else topicNameTest
        val bootstrapServer = if (kafkaEnv == "prod") bootstrapServerProd else bootstrapServerTest



        val consumerProperties = new Properties()
        consumerProperties.setProperty("bootstrap.servers", bootstrapServer)
        consumerProperties.setProperty("group.id", groupId)

        val kafkaSource = new FlinkKafkaConsumer[SnowplowRecord](
            topicName,
            SnowplowDeserializationSchema,
            consumerProperties
        )

        val eventStream : DataStream[SnowplowRecord] = flinkEnv.addSource(kafkaSource)

        val newAggregatedStream: DataStream[AggregatePageview] =
            eventStream.map(new MyMapFunction)

        val newFilteredStream: DataStream[AggregatePageview] =
            newAggregatedStream.filter(new MyFilterFunction)

        newFilteredStream.print()

        flinkEnv.execute("Local Snowplow Events QA")

    }

    def main(args: Array[String]): Unit = {

        println("Aggregation App starts...")

        localCompute()

    }


    class MyMapFunction extends MapFunction[SnowplowRecord, AggregatePageview] {

        override def map(snowplowRecord: SnowplowRecord): AggregatePageview = {
            val snowplowEvent = snowplowRecord.value match {
                case Some(event: Event) => event
            }

            return AggregatePageview(
                eventType = snowplowEvent.event.getOrElse("Missing"),
                uri = snowplowEvent.page_url.getOrElse("Missing"),
                relationId = snowplowEvent.app_id.getOrElse("Missing"),
                views = 1,
                timestamp = snowplowEvent.collector_tstamp,
                referrer = snowplowEvent.refr_source.getOrElse("Missing"),
                referrerPath = snowplowEvent.refr_urlpath.getOrElse("Missing")
            )
        }
    }


    class MyFilterFunction extends FilterFunction[AggregatePageview] {

        override def filter(aggregatePageview: AggregatePageview): Boolean = {

            if (aggregatePageview.eventType == "page_view") true else false

        }

    }

}

















