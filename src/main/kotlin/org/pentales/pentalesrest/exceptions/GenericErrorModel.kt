package org.pentales.pentalesrest.exceptions

import org.springframework.http.*

class GenericErrorModel(
    val status: String, val message: String, val timestamp: Long, val statusCode: HttpStatusCode
) {}