//package net.persgroep.smartocto.sandbox
//
//import com.snowplowanalytics.snowplow.analytics.scalasdk.Event
//import io.circe.{Json, parser}
//
//import java.util.UUID
//import java.time.Instant
//
//object SnowplowEventParser {
//
//    def main(args: Array[String]) : Unit = {
//
//        val uuid = UUID.randomUUID()
//        val tstamp = Instant.now()
//
//        val eventJsonString = "{\"etl_tstamp\":\"2022-08-04T15:05:19.097Z\",\"dvce_ismobile\":false,\"geo_latitude\":51.2103,\"br_version\":\"1333.0.4\",\"v_collector\":\"ssc-2.6.1-kinesis\",\"collector_tstamp\":\"2022-08-04T15:05:17.995Z\",\"os_family\":\"Mac OS X\",\"event_vendor\":\"com.google.analytics\",\"network_userid\":\"f02f9c70-b468-476e-a54d-b02f455b2b92\",\"br_renderengine\":\"OTHER\",\"br_lang\":\"nl-BE\",\"geo_country\":\"BE\",\"event\":\"struct\",\"user_ipaddress\":\"78.20.203.104\",\"geo_region\":\"VOV\",\"geo_timezone\":\"Europe/Brussels\",\"os_manufacturer\":\"Apple Inc.\",\"event_format\":\"jsonschema\",\"geo_zipcode\":\"9190\",\"contexts\":{\"contexts_com_snowplowanalytics_mobile_screen_1\":[{\"name\":\"player\",\"id\":\"A6657079-5CEB-4C6B-AA39-3C463B3491D7\",\"type\":\"player\"}],\"contexts_com_iab_snowplow_spiders_and_robots_1\":[{\"spiderOrRobot\":false,\"category\":\"BROWSER\",\"reason\":\"PASSED_ALL\",\"primaryImpact\":\"NONE\"}],\"contexts_com_snowplowanalytics_snowplow_mobile_context_1\":[{\"deviceManufacturer\":\"Apple Inc.\",\"osVersion\":\"15.5\",\"osType\":\"ios\",\"deviceModel\":\"iPhone14,2\",\"networkType\":\"wifi\",\"appleIdfv\":\"D719EDD6-09FD-4639-B036-1B4F022CF605\"}],\"contexts_com_snowplowanalytics_snowplow_client_session_1\":[{\"previousSessionId\":\"2bd2f155-1593-4b2b-a4b0-ded5ca0f6ed4\",\"userId\":\"3657f248-733c-4be2-afe8-20b17297f28c\",\"sessionId\":\"457b16f7-e509-4903-a135-fd8d96fe874c\",\"firstEventId\":\"C57E55DD-4827-4D43-A5CF-17F073BDFBE7\",\"sessionIndex\":81,\"storageMechanism\":\"LOCAL_STORAGE\"}],\"contexts_com_snowplowanalytics_snowplow_ua_parser_context_1\":[{\"useragentFamily\":\"QmusicBE\",\"useragentMajor\":\"1\",\"useragentVersion\":\"QmusicBE 1\",\"osFamily\":\"iOS\",\"osVersion\":\"iOS\",\"deviceFamily\":\"iOS-Device\"}],\"contexts_nl_basjes_yauaa_context_1\":[{\"deviceBrand\":\"Apple\",\"deviceName\":\"Apple iOS Device\",\"operatingSystemVersionMajor\":\"21\",\"layoutEngineNameVersion\":\"QmusicBE 1\",\"operatingSystemNameVersion\":\"Darwin (iOS) 21.5.0\",\"layoutEngineNameVersionMajor\":\"QmusicBE 1\",\"operatingSystemName\":\"Darwin (iOS)\",\"agentVersionMajor\":\"1\",\"layoutEngineVersionMajor\":\"1\",\"deviceClass\":\"Mobile\",\"agentNameVersionMajor\":\"QmusicBE 1\",\"operatingSystemNameVersionMajor\":\"Darwin (iOS) 21\",\"operatingSystemClass\":\"Mobile\",\"layoutEngineName\":\"QmusicBE\",\"agentName\":\"QmusicBE\",\"agentVersion\":\"1\",\"layoutEngineClass\":\"Mobile App\",\"agentNameVersion\":\"QmusicBE 1\",\"operatingSystemVersion\":\"21.5.0\",\"agentClass\":\"Mobile App\",\"layoutEngineVersion\":\"1\"}],\"contexts_com_snowplowanalytics_mobile_application_1\":[{\"build\":\"1\",\"version\":\"270\"}],\"contexts_nl_persgroep_custom_data_1\":[{\"advertising1\":\"true\",\"functional\":\"true\",\"gtmContainerId\":\"GTM-TLCV8LK\",\"gtmContainerVersion\":\"58\",\"personalisation\":\"true\",\"marketing\":\"true\",\"method\":\"event\",\"advertising9\":\"true\",\"socialMedia\":\"true\",\"analytics\":\"true\",\"platform\":\"App\",\"advertising7\":\"true\",\"cxenseId\":\"00000000-0000-0000-0000-000000000000\",\"advertising4\":\"true\",\"advertising10\":\"true\",\"privacySettings\":\"functional|analytics|social_media|targeted_advertising|advertising_1|advertising_2|advertising_3|advertising_4|advertising_7|advertising_9|advertising_10|personalisation|marketing\",\"loggedin\":\"\",\"advertising3\":\"true\",\"advertising2\":\"true\",\"targetedAdvertising\":\"true\",\"content\":\"Radio\",\"accountId\":\"user_id\"}]},\"br_family\":\"CFNetwork\",\"useragent\":\"QmusicBE/1 CFNetwork/1333.0.4 Darwin/21.5.0\",\"event_name\":\"event\",\"os_name\":\"Mac OS X\",\"br_name\":\"CFNetwork\",\"ip_organization\":\"Telenet\",\"dvce_created_tstamp\":\"2022-08-04T15:05:17.964Z\",\"dvce_type\":\"Computer\",\"dvce_sent_tstamp\":\"2022-08-04T15:05:17.979Z\",\"se_action\":\"app_background\",\"se_category\":\"app interaction\",\"geo_longitude\":4.0402,\"v_tracker\":\"ios-2.2.2\",\"os_timezone\":\"Europe/Brussels\",\"br_type\":\"unknown\",\"event_version\":\"1-0-0\",\"dvce_screenwidth\":1170,\"geo_location\":\"51.2103,4.0402\",\"name_tracker\":\"dpg\",\"unstruct_event\":{},\"dvce_screenheight\":2532,\"br_viewwidth\":1170,\"geo_city\":\"Stekene\",\"br_viewheight\":2532,\"derived_tstamp\":\"2022-08-04T15:05:17.980Z\",\"app_id\":\"qmusic.ios\",\"ip_isp\":\"Telenet\",\"geo_region_name\":\"East Flanders Province\",\"platform\":\"mob\",\"event_id\":\"9f409867-181d-43b4-b049-34945b02c34d\",\"event_fingerprint\":\"7f5a96733b123c25084645a5150bfa70\",\"v_etl\":\"snowplow-enrich-kinesis-3.1.3-common-3.1.3\"}"
//
//        val derivedCtxFix = parser.parse("""{"derived_contexts": {} }""").getOrElse(Json.Null)
//
//        val eventJson : Json = parser.parse(eventJsonString).toOption match {
//            case Some(v) => v
//            case None => Json.Null
//        }
//
//        val fixedJson = eventJson.deepMerge(derivedCtxFix)
//
//        val snowplowEvent = Event.eventDecoder.decodeJson(fixedJson)
//
//        println(snowplowEvent.toString)
//
//
//
////        val event = Event.minimal(
////            uuid,
////            tstamp,
////            "vCollector_1.1.0",
////            "vEtl_2.1.0"
////        )
////
////        println(event)
//
//    }
//
//}
