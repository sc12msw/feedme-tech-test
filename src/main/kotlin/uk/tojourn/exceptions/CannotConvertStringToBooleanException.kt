package uk.tojourn.exceptions

import java.lang.Exception

class CannotConvertStringToBooleanException (error: String) : Exception(error)