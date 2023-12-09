package org.pentales.pentalesrest.security

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.*
import org.pentales.pentalesrest.exceptions.*
import org.springframework.http.*
import org.springframework.security.core.*
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint

class JwtAuthenticationEntryPoint : Http403ForbiddenEntryPoint() {

    override fun commence(
        request: HttpServletRequest?, response: HttpServletResponse?, authException: AuthenticationException?
    ) {
        if (response  == null) throw GenericException("Response is null")

        val status = HttpStatus.UNAUTHORIZED

        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.status = status.value()

        val errorModel = GenericErrorModel(
            authException?.message ?: "Authentication failed",
            System.currentTimeMillis(),
            status.value(),
            authException!!,
        )
        val outputStream = response.outputStream
        val mapper = ObjectMapper()
        mapper.writeValue(outputStream, errorModel)
        outputStream.flush()
    }
}