package uk.tojourn.client

import com.google.gson.Gson
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.Producer
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.StringSerializer
import java.net.Socket
import org.apache.logging.log4j.kotlin.Logging
import uk.tojourn.deserialisers.FeedMeDeserializer
import java.io.InputStreamReader
import java.io.BufferedReader
import java.lang.Exception
import java.util.*


class FeedMeProducerClient {

    companion object : Logging

    //TODO Pass this as config
    private val topic = "dev.betting"

    //TODO pass args as config
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

    private fun deserializeMessageAndAddToQueue(bufferedReader: BufferedReader, producer: Producer<String,String>) {
        while (true) {
            val line = bufferedReader.readLine() ?: break
            val deserializer = FeedMeDeserializer()
            val dataObject = deserializer.extractHeaderAndBodyFromString(line)
            val gson = Gson()
            val jsonString = gson.toJson(dataObject)
            producer.send(ProducerRecord(topic, jsonString))
        }
    }

    private fun createProducer(brokers: String): Producer<String, String> {
        val props = Properties()
        props["bootstrap.servers"] = brokers
        props["key.serializer"] = StringSerializer::class.java
        props["value.serializer"] = StringSerializer::class.java
        return KafkaProducer(props)
    }
}
