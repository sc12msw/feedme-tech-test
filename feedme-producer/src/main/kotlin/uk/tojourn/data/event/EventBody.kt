package uk.tojourn.data.event

data class EventBody(val eventId: String,
                     val category: String,
                     val subCategory : String,
                     val name: String,
                     val startTime: Long,
                     val displayed: Boolean,
                     val suspended: Boolean)