package uk.tojourn

import com.google.inject.Guice
import uk.tojourn.client.FeedMeConsumerClient
import uk.tojourn.dimodules.FeedMeModule

fun main() {

   val injector = Guice.createInjector(FeedMeModule())
   val client = injector.getInstance(FeedMeConsumerClient::class.java)
   client.start()


}

