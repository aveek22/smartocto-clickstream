package net.persgroep.smartocto.models

import java.time.Instant

case class AggregatePageview(
                            eventType: String,
                            uri: String,
                            relationId: String,
                            views: Int,
                            timestamp: Instant,
                            referrer: String,
                            referrerPath: String,
                            )
