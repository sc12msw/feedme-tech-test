package uk.tojourn.data

data class OutcomeBody (val marketId:String,
                        val outcomeId:String,
                        val name:String,
                        val price:String,
                        val displayed: Boolean,
                        val suspended: Boolean)