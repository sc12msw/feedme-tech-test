package uk.tojourn.dimodules

import com.google.inject.AbstractModule
import com.google.inject.Provides
import com.mongodb.DBCollection
import com.mongodb.MongoClient
import com.mongodb.WriteConcern
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import org.apache.kafka.clients.consumer.Consumer
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.serialization.StringDeserializer
import org.bson.Document
import java.util.*

class FeedMeModule : AbstractModule() {

    private val collectionName = "feedMeCollection"

    @Provides
    fun mongoCollection(): MongoCollection<Document> {
        //TODO fetch from config
        val database = mongoDb()
        if (!checkIfCollectionExists(database)) {
            database.createCollection(collectionName);
        }
        return database.getCollection(collectionName);
    }

    @Provides
    fun mongoDb(): MongoDatabase {
        //TODO fetch from config
        val client = MongoClient("mongo" , 27017)
        return client.getDatabase("feedme")

    }

    @Provides
    fun createConsumer(): Consumer<String, String> {
        val props = Properties()
        //TODO fetch from config
        props["bootstrap.servers"] = "kafka:9092"
        props["group.id"] = "feedme-consumer"
        props["key.deserializer"] = StringDeserializer::class.java
        props["value.deserializer"] = StringDeserializer::class.java
        return KafkaConsumer<String, String>(props)
    }


    private fun checkIfCollectionExists(database: MongoDatabase): Boolean{
        val collectionNames = database.listCollectionNames()
        for (collection in collectionNames){
            if (collection == collectionName){
                return true
            }
        }
        return false
    }


}
