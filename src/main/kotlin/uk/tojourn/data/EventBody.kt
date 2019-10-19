package uk.tojourn.data

data class EventBody(val eventId: String,
                     val category: String,
                     val subCategory : String,
                     val name: String,
                     val startTime: Int,
                     val displayed: Boolean,
                     val suspended: Boolean)