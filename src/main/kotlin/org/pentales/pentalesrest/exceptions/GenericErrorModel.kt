package org.pentales.pentalesrest.exceptions

import com.fasterxml.jackson.annotation.JsonIgnore

class GenericErrorModel(
    val message: String,
    val timestamp: Long,
    val statusCode: Int,
    @JsonIgnore
    val exception: Exception,
) {}