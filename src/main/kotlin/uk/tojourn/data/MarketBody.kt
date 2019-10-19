package uk.tojourn.data

data class MarketBody (val eventId: String,
                       val marketId: String,
                       val name: String,
                       val displayed: Boolean,
                       val suspended: Boolean)