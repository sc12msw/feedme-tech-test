package uk.tojourn.deserialisers

import com.google.gson.Gson
import uk.tojourn.FeedMeClient
import uk.tojourn.data.EventBody
import uk.tojourn.data.HeaderAndBody
import uk.tojourn.data.Header
import uk.tojourn.data.OutcomeBody
import uk.tojourn.exceptions.DeserializationException


class FeedMeDeserializer() {

    private val regex = Regex("(?<!\\|\\|)")

    fun extractHeaderAndBodyFromString(line: String): HeaderAndBody {
        println(line)
        val split = line.split(regex)
        try {
            val header = Header(split[1].toInt(), split[2], split[3], split[4].toLong())
            var body = split.drop(5)
            body = body.dropLast(1)
            val jsonBody = createJsonBody(header.type, body)
            return HeaderAndBody(header, jsonBody)
        } catch (e: Exception) {
            FeedMeClient.logger.error("Deserialization error $e")
            throw DeserializationException("An error occurred during deserialization")
        }
    }

    private fun createJsonBody(type: String, body: List<String>): Any {
        try {
            val gson = Gson()
            return when (type) {
                "event" -> EventBody(
                        body[0],
                        body[1],
                        body[2],
                        body[3],
                        body[4].toInt(),
                        body[5].toBoolean(),
                        body[6].toBoolean()
                    )
                "outcome" ->
                    OutcomeBody(
                        body[0],
                        body[1],
                        body[2],
                        body[3],
                        body[4].toBoolean(),
                        body[5].toBoolean()
                    )

                else -> "Didnt work"
            }
        } catch (e: Exception) {
            FeedMeClient.logger.error("Service has performed a bail winton $e")
            throw DeserializationException("Cant convert to json")
        }

    }
}