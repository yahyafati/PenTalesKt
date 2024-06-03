package org.pentales.pentalesrest.exceptions

class NoEntityFoundException(
    message: String,
    cause: Throwable? = null
) : RuntimeException(message, cause)