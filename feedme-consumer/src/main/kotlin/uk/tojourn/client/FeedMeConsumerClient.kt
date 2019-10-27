package uk.tojourn.client

import com.google.inject.Inject
import org.apache.kafka.clients.consumer.Consumer
import org.apache.logging.log4j.kotlin.Logging


class FeedMeConsumerClient @Inject constructor(private val consumer: Consumer<String, String>) {

    companion object: Logging

    //TODO Pass this as config
    private val topic = "dev.betting"

    fun start(){
        consumeDataAndWriteToDB(consumer)
    }

    private fun consumeDataAndWriteToDB(consumer: Consumer<String, String>){
        while(true) {
            consumer.subscribe(listOf(topic))
            val records = consumer.poll(1000)
            logger.info("Received ${records.count()} records")
            for ( item in records){
                logger.info(item.value())
            }
        }
    }



}