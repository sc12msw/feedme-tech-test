package uk.tojourn.client

import com.google.gson.Gson
import com.google.inject.Inject
import org.apache.kafka.clients.producer.Producer
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.logging.log4j.kotlin.Logging
import uk.tojourn.deserialisers.FeedMeDeserializer
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.Socket


class FeedMeProducerClient @Inject constructor(private val producer: Producer<String, String>, private val providerConnection: Socket){

    companion object : Logging

    //TODO Pass this as config
    private val topic = "dev.betting"

    fun start(): Boolean {
        try {
            val bufferedReader = BufferedReader(InputStreamReader(providerConnection.getInputStream()))
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
            logger.info("Sent message ${dataObject.header.msgId} to kafka queue")
        }
    }


}
