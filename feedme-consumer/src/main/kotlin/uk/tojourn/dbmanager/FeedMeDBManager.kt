package uk.tojourn.dbmanager

import com.google.gson.Gson
import com.google.inject.Inject
import com.mongodb.client.MongoCollection
import org.bson.Document
import uk.tojourn.data.hierarchical.Event


class FeedMeDBManager @Inject constructor(private val collection: MongoCollection<Document>){

    fun createEvent(event: Event): Boolean{
        val gson = Gson()
        val document = Document.parse(
            gson.toJson(event)
        )
        collection.insertOne(document)
        return true
    }

    // This method was used for writing everything received from the queue to the db for testing
     fun createRaw(jsonString: String) : Boolean{
         val document = Document.parse(
             jsonString
         )
         collection.insertOne(document)
         return true
     }
}