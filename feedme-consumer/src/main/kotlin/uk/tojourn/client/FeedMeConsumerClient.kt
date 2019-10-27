package uk.tojourn.client

import org.apache.kafka.clients.consumer.Consumer
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.logging.log4j.kotlin.Logging
import java.time.Duration
import java.util.*


class FeedMeConsumerClient {

    companion object: Logging

    //TODO Pass this as config
    private val topic = "dev.betting"

    //TODO pass args as config
    fun start(kafkaHost: String, kafkaPort: Int){
        val kafkaBroker = kafkaHost.plus(":").plus(kafkaPort)
        val consumer = createConsumer(kafkaBroker)
        consumeDataAndWriteToDB(consumer)
    }

    private fun createConsumer(brokers: String): Consumer<String, String> {
        val props = Properties()
        props["bootstrap.servers"] = brokers
        props["group.id"] = "feedme-consumer"
        props["key.deserializer"] = StringDeserializer::class.java
        props["value.deserializer"] = StringDeserializer::class.java
        return KafkaConsumer<String, String>(props)
    }

    private fun consumeDataAndWriteToDB(consumer: Consumer<String, String>){
        while(true) {
            consumer.subscribe(listOf(topic))
            val records = consumer.poll(1000)
            logger.info("Received ${records.count()} records")
            for ( item in records){
                logger.info(item.value())
            }
        }
    }



}