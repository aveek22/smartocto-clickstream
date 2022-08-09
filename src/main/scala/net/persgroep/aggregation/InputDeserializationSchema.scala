package net.persgroep.aggregation

import io.circe.{HCursor, Json, parser}
import org.apache.flink.api.common.typeinfo.TypeInformation
import org.apache.flink.api.java.typeutils.TypeExtractor
import org.apache.flink.streaming.connectors.kafka.KafkaDeserializationSchema
import org.apache.kafka.clients.consumer.ConsumerRecord

object InputDeserializationSchema extends KafkaDeserializationSchema[InputRecord] {
    override def isEndOfStream(nextElement: InputRecord): Boolean = false

    override def deserialize(record: ConsumerRecord[Array[Byte], Array[Byte]]): InputRecord = {

        // TODO: Create a string from the Kafka message
        val recordValueString = new String(record.value())


        // TODO: Parse the string into a JSON object
        val recordValueJson: Json = parser.parse(recordValueString).toOption match {
            case Some(value) => value
            case None => Json.Null
        }

        val cursor: HCursor = recordValueJson.hcursor

        val event: String = cursor.downField("event").as[String].getOrElse("empty_event")
        val page: String = cursor.downField("page").as[String].getOrElse("empty_page")

        InputRecord(event, page)

    }

    override def getProducedType: TypeInformation[InputRecord] = TypeExtractor.getForClass(classOf[InputRecord])
}
