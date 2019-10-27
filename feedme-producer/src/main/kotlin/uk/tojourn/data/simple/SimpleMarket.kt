package uk.tojourn.data.simple

import uk.tojourn.data.generic.FeedMeDataType
import uk.tojourn.data.generic.HeaderAndBody

data class SimpleMarket(val market: HeaderAndBody): FeedMeDataType {

    override fun getOperation(): String {
        return market.header.operation
    }
    override fun getType():String {
        return market.header.type
    }
}