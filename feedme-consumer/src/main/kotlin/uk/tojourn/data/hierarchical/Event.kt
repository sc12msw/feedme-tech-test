package uk.tojourn.data.hierarchical


import uk.tojourn.data.event.EventBody

data class Event(val event: EventBody, val market: List<Market>)