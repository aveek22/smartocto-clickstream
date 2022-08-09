package net.persgroep.smartocto.models

import com.snowplowanalytics.snowplow.analytics.scalasdk.Event

case class SnowplowRecord(
                           key: Array[Byte],
                           topic: String,
                           value: Option[Event]
                         )
