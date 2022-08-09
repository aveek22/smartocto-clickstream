package net.persgroep.smartocto.serialization

import com.snowplowanalytics.snowplow.analytics.scalasdk.Event
import io.circe.{Json, parser}
import net.persgroep.smartocto.models.SnowplowRecord
import org.apache.flink.api.common.typeinfo.TypeInformation
import org.apache.flink.api.java.typeutils.TypeExtractor
import org.apache.flink.streaming.connectors.kafka.KafkaDeserializationSchema
import org.apache.kafka.clients.consumer.ConsumerRecord

import javax.swing.text.html.HTMLEditorKit.ParserCallback

object SnowplowDeserializationSchema extends KafkaDeserializationSchema[SnowplowRecord] {
    override def isEndOfStream(nextElement: SnowplowRecord): Boolean = false

    override def deserialize(record: ConsumerRecord[Array[Byte], Array[Byte]]): SnowplowRecord = {

        // TODO: Create a string from the Kafka message
        val recordValueString = new String(record.value())
          .replace("unstruct_events", "unstruct_event")


        // TODO: Parse the string into a JSON object
        val recordValueJson: Json = parser.parse(recordValueString).toOption match {
            case Some(value) => value
            case None => Json.Null
        }

        // TODO: Convert the JSON object into a Snowplow Event object
        val derivedCtxFix = parser.parse("""{"derived_contexts": {} }""").getOrElse(Json.Null)
        val fixedJson = recordValueJson.deepMerge(derivedCtxFix)


        val snowplowEvent = Event.eventDecoder.decodeJson(fixedJson).toOption

        SnowplowRecord(record.key(), record.topic(), snowplowEvent)

    }

    override def getProducedType: TypeInformation[SnowplowRecord] = TypeExtractor.getForClass(classOf[SnowplowRecord])
}
