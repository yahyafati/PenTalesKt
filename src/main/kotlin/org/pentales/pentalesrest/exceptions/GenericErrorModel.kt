package org.pentales.pentalesrest.exceptions

import com.fasterxml.jackson.annotation.*

data class GenericErrorModel(
    val message: String,
    val timestamp: Long = System.currentTimeMillis(),
    val status: Int,
    @JsonIgnore
    val exception: Exception? = null
) {

    val exceptionMessage: String? = exception?.message
    val exceptionClass: String? = exception?.javaClass?.name

    override fun toString(): String {
        return "GenericErrorModel(message='$message', timestamp=$timestamp, statusCode=$status, exceptionMessage=$exceptionMessage, exceptionClass=$exceptionClass)"
    }

}