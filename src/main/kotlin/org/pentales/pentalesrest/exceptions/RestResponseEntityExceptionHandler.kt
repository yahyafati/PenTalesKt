package org.pentales.pentalesrest.exceptions

import org.slf4j.LoggerFactory
import org.springframework.http.*
import org.springframework.security.authentication.*
import org.springframework.web.bind.annotation.*
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.context.request.*
import org.springframework.web.servlet.mvc.method.annotation.*

@RestControllerAdvice
class RestResponseEntityExceptionHandler : ResponseEntityExceptionHandler() {

    companion object {

        private val LOG = LoggerFactory.getLogger(RestResponseEntityExceptionHandler::class.java)
    }

    @ExceptionHandler(value = [Exception::class])
    protected fun handleConflict(ex: Exception, request: WebRequest?): ResponseEntity<Any>? {
        ex.printStackTrace()
        val bodyOfResponse = ex.message
        when (ex) {
            is AuthenticationCredentialsNotFoundException -> return handleUnauthorized(ex, request)
        }
        return handleExceptionInternal(
            ex, bodyOfResponse, HttpHeaders(), HttpStatus.BAD_REQUEST, request!!
        )
    }

    @ExceptionHandler(value = [AuthenticationCredentialsNotFoundException::class])
    protected fun handleUnauthorized(ex: AuthenticationCredentialsNotFoundException, request: WebRequest?): ResponseEntity<Any>? {
        val bodyOfResponse = ex.message
        return handleExceptionInternal(
            ex, bodyOfResponse, HttpHeaders(), HttpStatus.UNAUTHORIZED, request!!
        )
    }
}
