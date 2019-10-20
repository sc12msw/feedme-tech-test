package uk.tojourn.data.hierarchical

import uk.tojourn.data.generic.HeaderAndBody
import uk.tojourn.data.generic.outcome.Outcome

data class Market(val market: HeaderAndBody, val outcome: List<Outcome>)