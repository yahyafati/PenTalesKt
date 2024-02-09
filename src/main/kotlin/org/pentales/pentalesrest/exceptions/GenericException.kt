package org.pentales.pentalesrest.exceptions

import org.springframework.http.*

class GenericException(
    override val message: String?,
    override val cause: Throwable? = null,
    val status: HttpStatus = HttpStatus.BAD_REQUEST,
) : RuntimeException(
    message, cause
) {

    override fun toString(): String {
        return "GenericException(message=$message, cause=$cause)"
    }
}