package uk.tojourn.messaging

import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.common.serialization.StringSerializer
import org.apache.logging.log4j.kotlin.Logging
import java.util.*

class Producer(private val brokers: String) {

    companion object : Logging

    private val producer = createProducer(brokers)

    private fun createProducer(brokers: String): KafkaProducer<String, String> {
        val props = Properties()
        props["bootstrap.servers"] = brokers
        props["key.serializer"] = StringSerializer::class.java
        props["value.serializer"] = StringSerializer::class.java
        return KafkaProducer(props)
    }

}