package uk.tojourn

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.apache.logging.log4j.kotlin.logger
val logger = logger("uk.tojurn.FeedMeApplication")
fun main(args: Array<String>) {
    if (args.size < 2) {
        println("Usage <ip or host> <port> e.g. 127.0.0.1 8282")
        return
    }
    try {
        val host = args[0]
        val port = args[1].toInt()
        val logger = logger("uk.tojourn.FeedMeApplication")
        val client = FeedMeClient()
        var stopped = client.start(host, port)
        while (stopped) {
            runBlocking {
                logger.info("Service has stopped receiving data from the server will try reconnect every 10 seconds")
                logger.info("Connecting...")
                stopped = client.start(host, port)
                delay(10000)
            }

        }
    }catch (e: Exception){
        logger.fatal("Application failed to start check you command line arguments are correct ")
    }
}

