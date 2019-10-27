package uk.tojourn.data.hierarchical

import uk.tojourn.data.market.MarketBody
import uk.tojourn.data.outcome.OutcomeBody

data class Market(val market: MarketBody, val outcome: List<OutcomeBody>)