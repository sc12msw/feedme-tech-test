package uk.tojourn

import com.google.gson.Gson
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.StringSerializer
import java.net.Socket
import org.apache.logging.log4j.kotlin.Logging
import uk.tojourn.deserialisers.FeedMeDeserializer
import java.io.InputStreamReader
import java.io.BufferedReader
import java.lang.Exception
import java.util.*


class FeedMeClient {

    companion object : Logging

    private val topic = "dev.betting"

    fun start(providerHost: String, providerPort: Int, kafkaHost: String, kafkaPort: Int): Boolean {
        try {
            val providerConnection = Socket(providerHost, providerPort)
            logger.info("Connected to server at $providerHost on providerPort $providerPort")
            val bufferedReader = BufferedReader(InputStreamReader(providerConnection.getInputStream()))
            val kafkaBroker = kafkaHost.plus(":").plus(kafkaPort)
            val producer = createProducer(kafkaBroker)
            deserializeMessageAndAddToQueue(bufferedReader, producer)
        } catch (e: Exception) {
            logger.error("The server has caused an error: $e")
        } finally {
            return true
        }
    }

    private fun deserializeMessageAndAddToQueue(bufferedReader: BufferedReader, producer: KafkaProducer<String,String>) {
        while (true) {
            val line = bufferedReader.readLine() ?: break
            val deserializer = FeedMeDeserializer()
            val dataObject = deserializer.extractHeaderAndBodyFromString(line)
            val gson = Gson()
            val jsonString = gson.toJson(dataObject)
            producer.send(ProducerRecord(topic, jsonString))
        }
    }

    private fun createProducer(brokers: String): KafkaProducer<String, String> {
        val props = Properties()
        props["bootstrap.servers"] = brokers
        props["key.serializer"] = StringSerializer::class.java
        props["value.serializer"] = StringSerializer::class.java
        return KafkaProducer(props)
    }
}
