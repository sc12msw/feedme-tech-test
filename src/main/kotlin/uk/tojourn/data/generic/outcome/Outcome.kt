package uk.tojourn.data.generic.outcome

import uk.tojourn.data.generic.FeedMeDataType
import uk.tojourn.data.generic.HeaderAndBody

data class Outcome(val outcome: HeaderAndBody): FeedMeDataType {
    override fun getOperation(): String {
        return outcome.header.operation
    }
    override fun getType(): String {
        return outcome.header.type
    }
}