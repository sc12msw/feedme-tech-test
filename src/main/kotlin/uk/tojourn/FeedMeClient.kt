package uk.tojourn

import com.google.gson.Gson
import kotlinx.coroutines.*
import java.net.Socket
import org.apache.logging.log4j.kotlin.Logging
import uk.tojourn.data.Event
import uk.tojourn.data.EventBody
import uk.tojourn.data.Header
import uk.tojourn.deserialisers.FeedMeDeserializer
import java.io.InputStreamReader
import java.io.BufferedReader


class FeedMeClient : CoroutineScope by CoroutineScope(Dispatchers.Default) {

    companion object : Logging

    fun start(host: String, port: Int) {
        val connection = Socket(host, port)
        logger.info("Connected to server at $host on port $port")

        val bufferedReader = BufferedReader(InputStreamReader(connection.getInputStream()))
        while (true) {
            val line = bufferedReader.readLine() ?: break
            val deserializer = FeedMeDeserializer()
            val dataObject = deserializer.extractHeaderAndBodyFromString(line)
            val eventObject = Event(dataObject)
            var gson = Gson()
            var jsonString = gson.toJson(eventObject)
            print(jsonString)
        }
    }
}
