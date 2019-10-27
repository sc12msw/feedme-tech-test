package uk.tojourn.client

import com.google.gson.Gson
import com.google.gson.internal.LinkedTreeMap
import com.google.inject.Guice
import com.google.inject.Inject
import org.apache.kafka.clients.consumer.Consumer
import org.apache.logging.log4j.kotlin.Logging
import uk.tojourn.data.HeaderAndBody
import uk.tojourn.data.event.EventBody
import uk.tojourn.data.hierarchical.Event
import uk.tojourn.data.hierarchical.Market
import uk.tojourn.dbmanager.FeedMeDBManager
import uk.tojourn.dimodules.FeedMeModule
import uk.tojourn.exceptions.CannotCreateEventException
import uk.tojourn.exceptions.DataConsumerException


class FeedMeConsumerClient @Inject constructor(private val consumer: Consumer<String, String>) {

    companion object: Logging

    //TODO Pass this as config
    private val topic = "dev.betting"

    private var creatingEventFlag = false
    private var creatingMarketFlag = false
    val gson = Gson()
    val injector = Guice.createInjector(FeedMeModule())
    val dbManager = injector.getInstance(FeedMeDBManager::class.java)

    fun start(){
        consumeData(consumer)
    }

    private fun consumeData(consumer: Consumer<String, String>){
        while(true) {
            consumer.subscribe(listOf(topic))
            val records = consumer.poll(1000)
            for (item in records){
                try {
                    val feedmeObject = gson.fromJson(item.value(), HeaderAndBody::class.java)
                    when (feedmeObject.header.operation) {
                        //TODO implement more than just create event (create child markets and outcomes)
                        "create" -> createProcessingAndWriteToDB(feedmeObject)
                        //TODO implement update
                        "update"-> logger.info("TODO This is where an update should happen")
                    }
                }catch (e: Exception){
                    logger.error("An error occurred when serialising to json. Error $e")
                    throw DataConsumerException("When trying to serialise to json an error occurred")
                }
            }
        }
    }

    private fun createProcessingAndWriteToDB(feedmeObject: HeaderAndBody){
        try {
            if (feedmeObject.header.type == "event") {
                val body = feedmeObject.body
                val eventId = body["eventId"] as String
                val category = body["category"] as String
                val subCategory = body["subCategory"] as String
                val name = body["name"] as String
                val startTime = body["startTime"] as Double
                val displayed = body["displayed"] as Boolean
                val suspended = body["suspended"] as Boolean
                val longStartTime = startTime.toLong()
                val eventBody = EventBody(eventId, category, subCategory, name, longStartTime, displayed, suspended)
                logger.info("Writing the event:".plus(eventBody.eventId).plus(" to DB"))

                //TODO Figure out a way to wait here for the list of markets and list of outcomes
                val marketList = listOf<Market>()
                dbManager.createEvent(Event(eventBody, marketList))
            }
        }catch (e: Exception){
            logger.error("An error occured when processing the data and saving to the db. Error $e")
            throw  CannotCreateEventException("Failure occurred during processing error :$e ")
        }

    }



}

