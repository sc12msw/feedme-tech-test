package uk.tojourn

import com.google.gson.Gson
import org.apache.logging.log4j.kotlin.Logging
import uk.tojourn.deserialisers.FeedMeDeserializer
import java.io.BufferedReader
import java.io.FileWriter
import java.io.InputStreamReader
import java.net.Socket


class FeedMeClient {

    companion object : Logging

    fun start(host: String, port: Int): Boolean {
        try {
            val connection = Socket(host, port)
            logger.info("Connected to server at $host on port $port")
            val bufferedReader = BufferedReader(InputStreamReader(connection.getInputStream()))
            readLinesFromBufferedReaderAndWriteToJsonFile(bufferedReader)
            connection.close()
        } catch (e: Exception) {
            logger.error("The server has caused an error: $e")
            return true
        }
        return false
    }


    private fun readLinesFromBufferedReaderAndWriteToJsonFile(bufferedReader: BufferedReader) {

        logger.info("Writing out put to json file, this is just for task one proof will only write 100 objects to file")
        var counter = 0
        FileWriter("./output/output.json").use { file ->
            file.write("{ \"taskOneOutputs\" : [")
            while (counter < 100) {
                val line = bufferedReader.readLine() ?: break
                val deserializer = FeedMeDeserializer()
                val dataObject = deserializer.extractHeaderAndBodyFromString(line)
                val gson = Gson()
                val jsonString = gson.toJson(dataObject)
                val dataId = dataObject.getMsgId()
                logger.info("Writing object to file with id: $dataId ")
                file.write(jsonString)
                counter++
                if (counter < 100){
                    file.write(",")
                }
            }
            file.write("]}")
        }
    }
}
