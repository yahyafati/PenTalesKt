package org.pentales.pentalesrest.exceptions

import org.springframework.http.*
import org.springframework.web.bind.annotation.*
import org.springframework.web.context.request.*
import org.springframework.web.servlet.mvc.method.annotation.*

@RestControllerAdvice
class RestResponseEntityExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(value = [Exception::class])
    protected fun handleConflict(ex: RuntimeException, request: WebRequest?): ResponseEntity<Any>? {
        ex.printStackTrace()
        val bodyOfResponse = ex.message ?: "Unknown error"
        val errorModel: GenericErrorModel = GenericErrorModel(
            "error", bodyOfResponse, System.currentTimeMillis(), HttpStatus.BAD_REQUEST
        )
        return handleExceptionInternal(
            ex, errorModel, HttpHeaders(), HttpStatus.BAD_REQUEST, request!!
        )
    }
}
