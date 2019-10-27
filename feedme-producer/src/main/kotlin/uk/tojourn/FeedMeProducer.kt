package uk.tojourn

import com.google.inject.Guice
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.apache.logging.log4j.kotlin.logger
import uk.tojourn.client.FeedMeProducerClient
import uk.tojourn.dimodules.FeedMeModule

val logger = logger("uk.tojourn.FeedMeProducer")
fun main() {
    try {
        runBlocking {
            val injector = Guice.createInjector(FeedMeModule())
            val client = injector.getInstance(FeedMeProducerClient::class.java)
            var tryReconnect = true
            while (tryReconnect) {
                logger.info("Connecting...")
                tryReconnect = client.start()
                logger.info("Service has stopped receiving data from the server will try reconnect every 10 seconds")
                delay(10000)
            }
        }
        logger.info("Application has finished being fed and is now full.")
    } catch (e: Exception) {
        logger.fatal("Application failed to start Error: $e")
    }
}

