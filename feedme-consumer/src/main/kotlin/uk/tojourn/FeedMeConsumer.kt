package uk.tojourn

import org.apache.logging.log4j.kotlin.logger
import uk.tojourn.client.FeedMeConsumerClient
import com.google.inject.Guice
import com.google.inject.Injector
import uk.tojourn.dimodules.FeedMeModule


val logger = logger("uk.tojourn.FeedMeConsumer")
fun main(args: Array<String>) {

   val injector = Guice.createInjector(FeedMeModule())
   val client = injector.getInstance(FeedMeConsumerClient::class.java)
   client.start()


}

