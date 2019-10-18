package uk.tojourn

import java.util.logging.Logger

class FeedMeClient {
    companion object {
        val log = Logger.getLogger(this::class.java.simpleName)
    }

    fun start() : Boolean{
        log.info("Client is starting....")
        return true
    }
}