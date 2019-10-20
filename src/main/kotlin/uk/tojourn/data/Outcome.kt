package uk.tojourn.data

data class Outcome(val outcome: HeaderAndBody): FeedMeDataType {
    override fun getOperation(): String {
        return outcome.header.operation
    }
    override fun getType(): String {
        return outcome.header.type
    }
    override fun getMsgId(): Int {
        return outcome.header.msgId
    }
}