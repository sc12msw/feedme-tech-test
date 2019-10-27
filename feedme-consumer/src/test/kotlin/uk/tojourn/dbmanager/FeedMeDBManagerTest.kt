package uk.tojourn.dbmanager

import com.google.gson.Gson
import com.mongodb.client.MongoCollection
import io.mockk.*
import org.bson.Document
import org.junit.jupiter.api.Test
import uk.tojourn.data.event.EventBody
import uk.tojourn.data.market.MarketBody
import uk.tojourn.data.outcome.OutcomeBody

import uk.tojourn.data.hierarchical.Event
import uk.tojourn.data.hierarchical.Market
import kotlin.test.assertTrue

class FeedMeDBManagerTest {

    private val validBodyOutcome = OutcomeBody(
        "45678", "987653", "|test|", "4/5",
        displayed = false,
        suspended = true
    )

    private val validBodyMarket = MarketBody(
        "127315", "45678", "|test|",
        displayed = false,
        suspended = true
    )

    private val validBodyEvent = EventBody(
        "127315", "test", "test", "|test|", 12345678902,
        displayed = false,
        suspended = true
    )
    private val validOutcomeList = listOf<OutcomeBody>(validBodyOutcome)
    private val validMarket = Market(validBodyMarket, validOutcomeList)
    private val validMarketList = listOf<Market>(validMarket)
    private val validEvent = Event(validBodyEvent, validMarketList)

    @Test
    fun `test create event returns true`() {
        val mockCollection = mockk<MongoCollection<Document>>()
        val mockDbManager = FeedMeDBManager(mockCollection)
        every { mockDbManager.createEvent(validEvent) } returns true
        val result = mockDbManager.createEvent(validEvent)
        assertTrue(result)
    }

    @Test
    fun `test create event calls insert one with event`() {
        val mockCollection = mockk<MongoCollection<Document>>()
        val mockDbManager = FeedMeDBManager(mockCollection)
        val gson = Gson()
        val document = Document.parse(
            gson.toJson(validEvent)
        )
        every { mockDbManager.createEvent(validEvent) } returns true
        val result = mockDbManager.createEvent(validEvent)
        verify { mockCollection.insertOne(document)}
        assertTrue(result)
    }


}