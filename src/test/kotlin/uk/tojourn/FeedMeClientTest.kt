package uk.tojourn

import org.junit.Test
import kotlin.test.assertTrue


class FeedMeClientTest {

    // Not going to continue with these tests as this is more of an integration test now than a unit test.
    @Test fun `test client has start method returns true when error occurs when connecting to a socket to indicate it should try reconnect`(){
        val client = FeedMeClient()
        assertTrue { client.start("atesthost", 1234) }
    }

}
