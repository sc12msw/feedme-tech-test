package uk.tojourn

import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class FeedMeClientTest {

    @Test fun `test client has start method returns true`(){
        val client = FeedMeClient()
        assertTrue { client.start() }
    }

}
