package uk.tojourn

import com.google.gson.Gson
import kotlinx.coroutines.*
import java.net.Socket
import org.apache.logging.log4j.kotlin.Logging
import uk.tojourn.data.*
import uk.tojourn.deserialisers.FeedMeDeserializer
import uk.tojourn.exceptions.TypeNotFoundException
import java.io.InputStreamReader
import java.io.BufferedReader
import java.lang.Exception


class FeedMeClient {

    companion object : Logging

    fun start(host: String, port: Int): Boolean {
        try {
            val connection = Socket(host, port)
            logger.info("Connected to server at $host on port $port")
            val bufferedReader = BufferedReader(InputStreamReader(connection.getInputStream()))
            readLinesFromBufferedReader(bufferedReader)
        } catch (e: Exception) {
            logger.error("The server has caused an error: $e")
        } finally {
            return true
        }
    }


    private fun readLinesFromBufferedReader(bufferedReader: BufferedReader) {
        while (true) {
            val line = bufferedReader.readLine() ?: break
            // TODO remove print replace with functionality
            print(line)
            val deserializer = FeedMeDeserializer()
            val dataObject = deserializer.extractHeaderAndBodyFromString(line)
            val gson = Gson()
            val jsonString = gson.toJson(dataObject)
            // TODO remove print replace with functionality
            print(jsonString)
        }
    }
}
