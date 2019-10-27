package uk.tojourn

import org.junit.Test
import kotlin.test.assertTrue


class FeedMeClientTest {

    @Test fun `test client has start method returns true when error occurs when connecting to a socket to indicate it should try reconnect`(){
        val client = FeedMeClient()
        assertTrue { client.start("atesthost", 1234, "testkafka", 5432) }
    }

}
