package uk.tojourn.data.hierarchical

import uk.tojourn.data.generic.HeaderAndBody

data class Event(val event: HeaderAndBody, val market: List<Market>)