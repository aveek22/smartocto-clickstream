package net.persgroep.clickstream.models

final case class SourceEvent(
    event_id    : Option[String],
    event       : Option[String],
    user_id     : Option[Int],
    event_time  : Option[String],
    os          : Option[String],
    page        : Option[String],
    url         : Option[String]
)
