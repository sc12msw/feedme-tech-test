package uk.tojourn.dimodules

import com.google.inject.AbstractModule
import com.google.inject.Provides
import com.mongodb.MongoClient
import com.mongodb.client.MongoDatabase
import org.apache.kafka.clients.consumer.Consumer
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.serialization.StringDeserializer
import java.util.*

class FeedMeModule : AbstractModule() {

    @Provides
    fun mongoDb(): MongoDatabase{
        //TODO fetch from config
        val client = MongoClient("mongo" , 27017)
        return client.getDatabase("feedme")
    }

    @Provides
    fun createConsumer(): Consumer<String, String> {
        val props = Properties()
        props["bootstrap.servers"] = "kafka:9092"
        props["group.id"] = "feedme-consumer"
        props["key.deserializer"] = StringDeserializer::class.java
        props["value.deserializer"] = StringDeserializer::class.java
        return KafkaConsumer<String, String>(props)
    }


}
