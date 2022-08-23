// package net.persgroep.clickstream.serialization

// import org.apache.flink.streaming.connectors.kafka.KafkaDeserializationSchema
// import net.persgroep.clickstream.models.SourceEvent
// import org.apache.kafka.clients.consumer.ConsumerRecord
// import org.apache.flink.api.common.typeinfo.TypeInformation
// import org.apache.flink.api.java.typeutils.TypeExtractor
// import java.nio.charset.StandardCharsets
// import io.circe.{Json, parser}

// object SourceDeserializationSchema extends KafkaDeserializationSchema[SourceEvent]{

//     override def deserialize(x: ConsumerRecord[Array[Byte],Array[Byte]]): SourceEvent = {

//         val jsonString = new String(x.value(), StandardCharsets.UTF_8).stripMargin

//         val json: Json = parser.parse(jsonString).toOption match {
//             case None => Json.Null
//             case Some(value) => value
//         }

//         // SourceEvent()

//     }

//     override def getProducedType(): TypeInformation[SourceEvent] = TypeExtractor.getForClass(classOf[SourceEvent])
        
//     override def isEndOfStream(x: SourceEvent): Boolean = false
  
// }
