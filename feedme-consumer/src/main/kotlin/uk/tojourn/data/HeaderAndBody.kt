package uk.tojourn.data

import com.google.gson.internal.LinkedTreeMap

data class HeaderAndBody (val header: Header, val body: LinkedTreeMap<Any, Any>)