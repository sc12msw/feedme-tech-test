package uk.tojourn.data

data class Event(val event: HeaderAndBody) : FeedMeDataType {
    override fun getOperation(): String {
        return event.header.operation
    }
    override fun getType():String {
        return event.header.type
    }
    override fun getMsgId(): Int {
        return event.header.msgId
    }
}