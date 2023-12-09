package org.pentales.pentalesrest.exceptions

import org.slf4j.*
import org.springframework.http.*
import org.springframework.security.authentication.*
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

    @ExceptionHandler(value = [AuthenticationCredentialsNotFoundException::class, GenericException::class])
    protected fun handleConflict(ex: Exception, request: WebRequest?): ResponseEntity<GenericErrorModel> {
        ex.printStackTrace()
        val bodyOfResponse = ex.message ?: "Unknown error"
        val errorModel = GenericErrorModel(
            bodyOfResponse, System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(), ex
        )
        when (ex) {
            is AuthenticationCredentialsNotFoundException -> return handleUnauthorized(errorModel, request)
            is GenericException -> return handleBadRequest(errorModel, request)
        }
        return ResponseEntity(
            errorModel, HttpHeaders(), HttpStatus.BAD_REQUEST,
        )
    }

    protected fun handleUnauthorized(errorModel: GenericErrorModel, request: WebRequest?): ResponseEntity<GenericErrorModel> {
        LOG.error(errorModel.message)
        return ResponseEntity(
            errorModel, HttpHeaders(), HttpStatus.UNAUTHORIZED
        )
    }

    protected fun handleBadRequest(errorModel: GenericErrorModel, request: WebRequest?): ResponseEntity<GenericErrorModel> {
        LOG.error(errorModel.message)
        return ResponseEntity(
            errorModel, HttpHeaders(), HttpStatus.BAD_REQUEST
        )
    }
}
