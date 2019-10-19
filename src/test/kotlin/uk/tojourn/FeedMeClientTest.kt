package uk.tojourn

import org.junit.Test
import org.mockito.Mockito
import uk.tojourn.deserialisers.FeedMeDeserializer
import java.io.ByteArrayInputStream
import java.net.Socket
import kotlin.test.assertTrue


class FeedMeClientTest {


    @Test fun `test client has start method returns true when error occures`(){
        val testString = "some data"
        val testStream =  ByteArrayInputStream(testString.toByteArray(Charsets.UTF_8))
        val mockSocket = Mockito.mock(Socket::class.java)
        Mockito.`when`(mockSocket. getInputStream()).thenReturn(testStream)
        val client = FeedMeClient()
        assertTrue { client.start("host", 1234) }
    }

}
