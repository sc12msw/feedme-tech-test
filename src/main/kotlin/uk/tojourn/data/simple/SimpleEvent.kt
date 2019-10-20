package uk.tojourn.data.simple

import uk.tojourn.data.generic.FeedMeDataType
import uk.tojourn.data.generic.HeaderAndBody

data class SimpleEvent(val event: HeaderAndBody) : FeedMeDataType {
    override fun getOperation(): String {
        return event.header.operation
    }
    override fun getType():String {
        return event.header.type
    }
}