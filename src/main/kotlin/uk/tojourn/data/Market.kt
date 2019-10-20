package uk.tojourn.data

data class Market(val market: HeaderAndBody): FeedMeDataType{

    override fun getOperation(): String {
        return market.header.operation
    }
    override fun getType():String {
        return market.header.type
    }
}