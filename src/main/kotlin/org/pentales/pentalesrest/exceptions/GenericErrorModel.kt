package org.pentales.pentalesrest.exceptions

import com.fasterxml.jackson.annotation.*

data class GenericErrorModel(
    val message: String,
    val timestamp: Long = System.currentTimeMillis(),
    val status: Int,
    @JsonIgnore
    val exception: Exception? = null,
    val extra: Any? = null
) {

    val exceptionClass: String? = exception?.javaClass?.name

    override fun toString(): String {
        return "GenericErrorModel(message='$message', timestamp=$timestamp, statusCode=$status, exceptionClass=$exceptionClass)"
    }

}