package org.pentales.pentalesrest.config.security

import com.fasterxml.jackson.databind.*
import jakarta.servlet.http.*
import org.pentales.pentalesrest.exceptions.*
import org.springframework.http.*
import org.springframework.security.core.*
import org.springframework.security.web.authentication.*

class JwtAuthenticationEntryPoint : Http403ForbiddenEntryPoint() {

    override fun commence(
        request: HttpServletRequest?, response: HttpServletResponse?, authException: AuthenticationException?
    ) {
        if (response == null) throw GenericException("Response is null")

        val status = HttpStatus.UNAUTHORIZED

        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.status = status.value()

        val errorModel = GenericErrorModel(
            message = authException?.message ?: "Authentication failed",
            timestamp = System.currentTimeMillis(),
            status = status.value(),
            exception = authException,
        )
        val outputStream = response.outputStream
        val mapper = ObjectMapper()
        mapper.writeValue(outputStream, errorModel)
        outputStream.flush()
    }
}