package uk.tojourn

fun main() {
    val client = FeedMeClient()
    client.start("127.0.0.1", 8282)
}

