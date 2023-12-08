package org.pentales.pentalesrest.exceptions

class GenericException(
    override val message: String?, override val cause: Throwable? = null
) : RuntimeException(
    message, cause
) {

    override fun toString(): String {
        return "GenericException(message=$message, cause=$cause)"
    }
}