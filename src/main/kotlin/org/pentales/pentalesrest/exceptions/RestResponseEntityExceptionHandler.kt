package org.pentales.pentalesrest.exceptions

import org.slf4j.*
import org.springframework.http.*
import org.springframework.security.access.*
import org.springframework.security.core.*
import org.springframework.web.bind.annotation.*
import org.springframework.web.context.request.*
import org.springframework.web.servlet.config.annotation.*
import org.springframework.web.servlet.mvc.method.annotation.*

@EnableWebMvc
@ControllerAdvice
class RestResponseEntityExceptionHandler : ResponseEntityExceptionHandler() {

    companion object {

        private val LOG = LoggerFactory.getLogger(RestResponseEntityExceptionHandler::class.java)
    }

    @ExceptionHandler(value = [Exception::class])
    protected fun handleConflict(ex: Exception, request: WebRequest?): ResponseEntity<GenericErrorModel> {
        val bodyOfResponse = ex.message ?: "Unknown error"

        when (ex) {
            is AuthenticationException -> return handleAuthenticationException(ex, request)
            is AccessDeniedException -> return handleAccessDenied(ex, request)
            is NoEntityWithIdException -> return handleNoEntityWithIdException(ex, request)
            is GenericException -> return handleBadRequest(ex, request)
        }

        LOG.error("Printing stack trace:")
        ex.printStackTrace()
        val errorModel = GenericErrorModel(
            bodyOfResponse,
            System.currentTimeMillis(),
            HttpStatus.BAD_REQUEST.value(),
            ex,
        )
        return ResponseEntity(
            errorModel, HttpHeaders(), HttpStatus.BAD_REQUEST,
        )
    }

    protected fun handleNoEntityWithIdException(
        exception: NoEntityWithIdException,
        request: WebRequest?
    ): ResponseEntity<GenericErrorModel> {
        val errorModel = GenericErrorModel(
            exception.message ?: "No entity with id",
            System.currentTimeMillis(),
            HttpStatus.NOT_FOUND.value(),
            exception,
        )
        LOG.error(errorModel.message)
        return ResponseEntity(
            errorModel, HttpHeaders(), HttpStatus.NOT_FOUND
        )
    }

    protected fun handleBadRequest(
        exception: GenericException,
        request: WebRequest?
    ): ResponseEntity<GenericErrorModel> {
        val errorModel = GenericErrorModel(
            exception.message ?: "Bad Request",
            System.currentTimeMillis(),
            HttpStatus.BAD_REQUEST.value(),
            exception,
        )
        LOG.error(errorModel.message)
        return ResponseEntity(
            errorModel, HttpHeaders(), HttpStatus.BAD_REQUEST
        )
    }

    protected fun handleAuthenticationException(
        exception: AuthenticationException,
        request: WebRequest?
    ): ResponseEntity<GenericErrorModel> {
        val errorModel = GenericErrorModel(
            exception.message ?: "Unauthorized",
            System.currentTimeMillis(),
            HttpStatus.UNAUTHORIZED.value(),
            exception,
        )
        LOG.error(errorModel.message)
        return ResponseEntity(
            errorModel, HttpHeaders(), HttpStatus.UNAUTHORIZED
        )
    }

    protected fun handleAccessDenied(
        exception: AccessDeniedException,
        request: WebRequest?
    ): ResponseEntity<GenericErrorModel> {
        val errorModel = GenericErrorModel(
            exception.message ?: "Access Denied",
            System.currentTimeMillis(),
            HttpStatus.FORBIDDEN.value(),
            exception,
        )
        LOG.error(errorModel.message)
        return ResponseEntity(
            errorModel, HttpHeaders(), HttpStatus.FORBIDDEN
        )
    }
}
