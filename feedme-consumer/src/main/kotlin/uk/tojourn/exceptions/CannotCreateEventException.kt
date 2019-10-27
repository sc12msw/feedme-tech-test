package uk.tojourn.exceptions

import java.lang.Exception

class CannotCreateEventException (error: String) : Exception(error)