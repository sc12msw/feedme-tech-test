package uk.tojourn.data.outcome

import uk.tojourn.data.FeedMeDataType
import uk.tojourn.data.HeaderAndBody

data class Outcome(val outcome: HeaderAndBody): FeedMeDataType {
    override fun getOperation(): String {
        return outcome.header.operation
    }
    override fun getType(): String {
        return outcome.header.type
    }
}