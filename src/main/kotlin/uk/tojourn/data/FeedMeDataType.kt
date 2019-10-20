package uk.tojourn.data

interface FeedMeDataType{
    fun getType(): String
    fun getOperation(): String
    fun getMsgId(): Int
}
