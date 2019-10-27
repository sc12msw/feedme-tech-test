package uk.tojourn.deserialisers

import uk.tojourn.client.FeedMeProducerClient
import uk.tojourn.data.Header
import uk.tojourn.data.HeaderAndBody
import uk.tojourn.data.event.EventBody
import uk.tojourn.data.market.MarketBody
import uk.tojourn.data.outcome.OutcomeBody
import uk.tojourn.exceptions.CannotConvertStringToBooleanException
import uk.tojourn.exceptions.DeserializationException
import uk.tojourn.exceptions.TypeNotFoundException


class FeedMeDeserializer() {

    private val cleanStringRegex = Regex("(\\\\\\|)")

    fun extractHeaderAndBodyFromString(line: String): HeaderAndBody {
        // TODO use correct regex so you don't have to replace the escaped pipes with a different symbol
        // This hack was due to me spending too much time on something simple would like some advice on the best way
        // of extracting this data into an array or even a completely different method
        val cleanLine = line.replace(cleanStringRegex, "*")
        val split = cleanLine.split("|")
        val formattedList = split.map { it.replace("*", "|") }
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////
        try {
            val header = Header(
                formattedList[1].toInt(),
                formattedList[2],
                formattedList[3],
                formattedList[4].toLong()
            )
            var body = formattedList.drop(5)
            body = body.dropLast(1)
            return createObjectType(header, body)
        } catch (e: CannotConvertStringToBooleanException) {
            FeedMeProducerClient.logger.error("Deserialization error $e")
            throw DeserializationException("An error occurred during the deserialization process, when processing the boolean values")
        } catch (e: Exception) {
            FeedMeProducerClient.logger.error("Deserialization error $e")
            throw DeserializationException("An error occurred during the deserialization process")
        }
    }

    private fun createObjectType(header: Header, body: List<String>): HeaderAndBody {

        return when (header.type) {
            "event" ->
                HeaderAndBody(
                    header,
                    EventBody(
                        body[0],
                        body[1],
                        body[2],
                        body[3],
                        body[4].toLong(),
                        stringToBoolean(body[5]),
                        stringToBoolean(body[6])
                    )
                )
            "market" ->
                HeaderAndBody(
                    header,
                    MarketBody(
                        body[0],
                        body[1],
                        body[2],
                        stringToBoolean(body[3]),
                        stringToBoolean(body[4])
                    )
                )
            "outcome" ->
                HeaderAndBody(
                    header,
                    OutcomeBody(
                        body[0],
                        body[1],
                        body[2],
                        body[3],
                        stringToBoolean(body[4]),
                        stringToBoolean(body[5])
                    )
                )
            else -> throw TypeNotFoundException("The type was not found was not known to the application")
        }
    }


    private fun stringToBoolean(string: String): Boolean {
        return when (string.toLowerCase()) {
            "1" -> true
            "0" -> false
            else -> throw CannotConvertStringToBooleanException("This string \"$string\" could not be converted to a boolean")
        }
    }
}