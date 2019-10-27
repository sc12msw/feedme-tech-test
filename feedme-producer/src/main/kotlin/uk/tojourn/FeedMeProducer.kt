package uk.tojourn

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.apache.logging.log4j.kotlin.logger
import uk.tojourn.client.FeedMeProducerClient

val logger = logger("uk.tojourn.FeedMeProducer")
fun main(args: Array<String>) {
    if (args.size != 4) {
        println("Usage <ip or host of provider> <provider port> <ip or host of kafka broker> <port of kafka broker> e.g. 127.0.0.1 8282 127.0.0.3 9092")
        return
    }
    try {
        runBlocking {
            val providerHost = args[0]
            val providerPort = args[1].toInt()
            val kafkaHost = args[2]
            val kafkaPort = args[3].toInt()
            val logger = logger("uk.tojourn.FeedMeApplication")
            val client = FeedMeProducerClient()
            var tryReconnect = true
            while (tryReconnect) {
                logger.info("Connecting...")
                tryReconnect = client.start(providerHost, providerPort, kafkaHost, kafkaPort)
                logger.info("Service has stopped receiving data from the server will try reconnect every 10 seconds")
                delay(10000)
            }
        }
        logger.info("Application has finished being fed and is now full.")
    } catch (e: Exception) {
        logger.fatal("Application failed to start check you command line arguments are correct and ports are integers")
    }
}

