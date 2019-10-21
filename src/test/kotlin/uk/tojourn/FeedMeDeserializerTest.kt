package uk.tojourn

import org.junit.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import uk.tojourn.data.*
import uk.tojourn.deserialisers.FeedMeDeserializer
import uk.tojourn.exceptions.DeserializationException
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class FeedMeDeserializerTest {

    private val validEvent =
        "|1|create|event|1234567891123|ffa75da5-a685-4b51-8fac-d2017e7ba96c|Skate Boarding Street|X Games|\\|P.J Ladd\\| vs \\|Eric Koston\\||1571565160773|0|1|"
    private val validMarket =
        "|12|create|market|1234567891123|ffa75da5-a685-4b51-8fac-d2017e7ba96c|be657094-b010-4809-9a93-be03b8bb97f6|After Three Round Points Result|0|1|"
    private val validOutcome =
        "|92|update|outcome|1234567891123|be657094-b010-4809-9a93-be03b8bb97f6|ae8e6f65-cc98-4b18-a994-ab6349950b86|\\|P.J Ladd\\||1/16|0|1|"
    private val testDeserializer = FeedMeDeserializer()

    @Test
    fun `test when a valid event from string is passed then method converts to correct object`() {
        val expectedEventHeader = Header(1, "create", "event", 1234567891123)
        val expectedEventBody = EventBody(
            "ffa75da5-a685-4b51-8fac-d2017e7ba96c",
            "Skate Boarding Street",
            "X Games",
            "|P.J Ladd| vs |Eric Koston|",
            1571565160773,
            displayed = false,
            suspended = true
        )
        val expectedHeaderAndBody = HeaderAndBody(expectedEventHeader, expectedEventBody)
        val expected = Event(expectedHeaderAndBody)
        val result = testDeserializer.extractHeaderAndBodyFromString(validEvent)
        assertEquals(expected, result)

    }

    @Test
    fun `test when a valid market from string is passed then method converts to correct object`() {
        val expectedEventHeader = Header(12, "create", "market", 1234567891123)
        val expectedEventBody = MarketBody(
            "ffa75da5-a685-4b51-8fac-d2017e7ba96c",
            "be657094-b010-4809-9a93-be03b8bb97f6",
            "After Three Round Points Result",
            displayed = false,
            suspended = true
        )
        val expectedHeaderAndBody = HeaderAndBody(expectedEventHeader, expectedEventBody)
        val expected = Market(expectedHeaderAndBody)
        val result = testDeserializer.extractHeaderAndBodyFromString(validMarket)
        assertEquals(expected, result)
    }

    @Test
    fun `test when a valid outcome from string is passed then method converts to correct object`() {
        val expectedEventHeader = Header(92, "update", "outcome", 1234567891123)
        val expectedEventBody = OutcomeBody(
            "be657094-b010-4809-9a93-be03b8bb97f6",
            "ae8e6f65-cc98-4b18-a994-ab6349950b86",
            "|P.J Ladd|",
            "1/16",
            displayed = false,
            suspended = true
        )
        val expectedHeaderAndBody = HeaderAndBody(expectedEventHeader, expectedEventBody)
        val expected = Outcome(expectedHeaderAndBody)
        val result = testDeserializer.extractHeaderAndBodyFromString(validOutcome)
        assertEquals(expected, result)
    }

    @ParameterizedTest
    @CsvSource(
        "type is not in schema, |1|create|test|1234567891123|ffa75da5-a685-4b51-8fac-d2017e7ba96c|Skate Boarding Street|X Games|\\|P.J Ladd\\| vs \\|Eric Koston\\||1571565160773|0|1|",
        "bad data string, something",
        "bad data number, 1",
        "id not number, |one|create|event|1234567891123|ffa75da5-a685-4b51-8fac-d2017e7ba96c|Skate Boarding Street|X Games|\\|P.J Ladd\\| vs \\|Eric Koston\\||1571565160773|0|1|",
        "displayed not known boolean, |1|create|event|1234567891123|ffa75da5-a685-4b51-8fac-d2017e7ba96c|Skate Boarding Street|X Games|\\|P.J Ladd\\| vs \\|Eric Koston\\||1571565160773|test|1|",
        "suspended not known boolean, |1|create|event|1234567891123|ffa75da5-a685-4b51-8fac-d2017e7ba96c|Skate Boarding Street|X Games|\\|P.J Ladd\\| vs \\|Eric Koston\\||1571565160773|0|test|",
        "body time stamp not long, |1|create|event|1234567891123|ffa75da5-a685-4b51-8fac-d2017e7ba96c|Skate Boarding Street|X Games|\\|P.J Ladd\\| vs \\|Eric Koston\\||notlong|0|test|",
        "header time stamp not long, |1|create|event|test|ffa75da5-a685-4b51-8fac-d2017e7ba96c|Skate Boarding Street|X Games|\\|P.J Ladd\\| vs \\|Eric Koston\\||1571565160773|0|1|",
        "header not correct length, |1 create|event|1234567891123|ffa75da5-a685-4b51-8fac-d2017e7ba96c|Skate Boarding Street|X Games|\\|P.J Ladd\\| vs \\|Eric Koston\\||1571565160773|0|1|",
        "body not correct length, |1|create event|1234567891123|ffa75da5-a685-4b51-8fac-d2017e7ba96c|Skate Boarding StreetX Games|\\|P.J Ladd\\| vs \\|Eric Koston\\||1571565160773|0|1|"
    )
    fun `test when bad headers and event bodies are passed then the object creates a Deserialization exception`(labelOfTest: String, input: String) {
        print("Testing: $labelOfTest \n")
        assertFailsWith<DeserializationException> {
            testDeserializer.extractHeaderAndBodyFromString(input)
        }
    }

    @ParameterizedTest
    @CsvSource(
        "not correct length, |12|create|market|1234567891123|ffa75da5-a685-4b51-8fac-d2017e7ba96c|be657094-b010-4809-9a93-be03b8bb97f6 After Three Round Points Result|0|1|",
        "displayed not known boolean, |12|create|market|1234567891123|ffa75da5-a685-4b51-8fac-d2017e7ba96c|be657094-b010-4809-9a93-be03b8bb97f6|After Three Round Points Result|test|1|",
        "suspended not known boolean, |12|create|market|1234567891123|ffa75da5-a685-4b51-8fac-d2017e7ba96c|be657094-b010-4809-9a93-be03b8bb97f6|After Three Round Points Result|0|test|"
    )
    fun `test when bad markets bodies are passed then the object creates a Deserialization exception`(labelOfTest: String, input: String) {
        print("Testing: $labelOfTest \n")
        assertFailsWith<DeserializationException> {
            testDeserializer.extractHeaderAndBodyFromString(input)
        }
    }
    @ParameterizedTest
    @CsvSource(
        "not correct length, |92|update|outcome|1234567891123|be657094-b010-4809-9a93-be03b8bb97f6 ae8e6f65-cc98-4b18-a994-ab6349950b86|\\|P.J Ladd\\||1/16|0|1|",
        "displayed not known boolean, |92|update|outcome|1234567891123|be657094-b010-4809-9a93-be03b8bb97f6|ae8e6f65-cc98-4b18-a994-ab6349950b86|\\|P.J Ladd\\||1/16|test|1|",
        "suspended not known boolean, |92|update|outcome|1234567891123|be657094-b010-4809-9a93-be03b8bb97f6|ae8e6f65-cc98-4b18-a994-ab6349950b86|\\|P.J Ladd\\||1/16|0|test|"
    )
    fun `test when bad outcome bodies are passed then the object creates a Deserialization exception`(labelOfTest: String, input: String) {
        print("Testing: $labelOfTest \n")
        assertFailsWith<DeserializationException> {
            testDeserializer.extractHeaderAndBodyFromString(input)
        }
    }
}
