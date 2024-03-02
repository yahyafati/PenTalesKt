package org.pentales.pentalesrest.exceptions

import org.springframework.validation.*

class ObjectValidationException(
    override val message: String,
    val errors: List<FieldError>
) : Exception(message) {

    override fun toString(): String {
        return "ObjectValidationError(message=$message, bindingResult=$errors)"
    }
}