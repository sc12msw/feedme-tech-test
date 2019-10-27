package uk.tojourn.dimodules

import com.google.inject.AbstractModule
import com.google.inject.Provides
import com.google.inject.Singleton
import org.apache.kafka.clients.consumer.Consumer
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.Producer
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.common.serialization.StringSerializer
import java.net.Socket
import java.util.*

class FeedMeModule : AbstractModule() {


    @Provides
    @Singleton
    fun createProducer(): Producer<String, String> {
        val props = Properties()
        props["bootstrap.servers"] = "kafka:9092"
        props["key.serializer"] = StringSerializer::class.java
        props["value.serializer"] = StringSerializer::class.java
        return KafkaProducer(props)
    }

    @Provides
    @Singleton
    fun createSocket(): Socket{
        return Socket("provider", 8282)
    }


}
