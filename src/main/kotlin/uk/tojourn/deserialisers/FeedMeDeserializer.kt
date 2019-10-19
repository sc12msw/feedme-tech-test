package uk.tojourn.deserialisers

import com.google.gson.Gson
import uk.tojourn.FeedMeClient
import uk.tojourn.data.*
import uk.tojourn.exceptions.DeserializationException
import uk.tojourn.exceptions.TypeNotFoundException


class FeedMeDeserializer() {

    private val cleanStringRegex = Regex("(\\\\\\|)")

    fun extractHeaderAndBodyFromString(line: String): HeaderAndBody {
        val cleanLine = line.replace(cleanStringRegex, "")
        val split = cleanLine.split("|")
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
            return when (type) {
                "event" -> EventBody(
                        body[0],
                        body[1],
                        body[2],
                        body[3],
                        body[4].toLong(),
                        body[5].toBoolean(),
                        body[6].toBoolean()
                    )
                "market" -> MarketBody(
                    body[0],
                    body[1],
                    body[2],
                    body[3].toBoolean(),
                    body[4].toBoolean()
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
                else -> throw TypeNotFoundException("The type was not found was not known to the application")
            }
        } catch (e: Exception) {
            FeedMeClient.logger.error("The service experienced and error when trying to deserialize, Error: $e")
            throw DeserializationException("Cant convert to json")
        }

    }
}