package org.pentales.pentalesrest.utils

import org.pentales.pentalesrest.exceptions.*
import org.springframework.validation.*

object ValidationUtils {

    fun validateAndGetErrors(
        validator: Validator,
        objectToValidate: Any,
        properties: List<String>? = null
    ): List<FieldError> {
        val errors: Errors = BeanPropertyBindingResult(objectToValidate, objectToValidate.javaClass.name)
        validator.validate(objectToValidate, errors)
        val validationErrors: MutableList<FieldError> = ArrayList()
        for (error in errors.fieldErrors) {
            validationErrors.add(error)
        }
        return if (properties != null) {
            validationErrors.filter { properties.contains(it.field) }
        } else {
            validationErrors
        }
    }

    fun validateAndThrow(validator: Validator, objectToValidate: Any, properties: List<String>? = null) {
        val errors = validateAndGetErrors(validator, objectToValidate, properties)
        if (errors.isNotEmpty()) {
            throw ObjectValidationException("Validation failed", errors)
        }
    }

}