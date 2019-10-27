package uk.tojourn

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.apache.logging.log4j.kotlin.logger
import uk.tojourn.client.FeedMeConsumerClient

val logger = logger("uk.tojourn.FeedMeConsumer")
fun main(args: Array<String>) {

   if (args.size != 2) {
      println("Usage <ip or host of kafka broker> <port of kafka broker> e.g. 127.0.0.3 9092")
      return
   }
   val kafkaHost = args[0]
   val kafkaPort = args[1].toInt()
   val client = FeedMeConsumerClient()
   client.start(kafkaHost, kafkaPort)

   logger.info("I am eating")
   logger.info("Your config".plus(args[0]).plus(args[1]))


}

