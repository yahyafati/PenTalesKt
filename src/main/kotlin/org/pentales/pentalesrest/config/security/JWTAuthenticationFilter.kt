package org.pentales.pentalesrest.config.security

import com.auth0.jwt.*
import com.auth0.jwt.algorithms.*
import com.fasterxml.jackson.databind.*
import jakarta.servlet.*
import jakarta.servlet.http.*
import org.pentales.pentalesrest.config.*
import org.pentales.pentalesrest.config.properties.*
import org.pentales.pentalesrest.global.dto.*
import org.pentales.pentalesrest.models.entities.*
import org.pentales.pentalesrest.models.entities.user.*
import org.pentales.pentalesrest.models.misc.jwt.*
import org.slf4j.*
import org.springframework.http.*
import org.springframework.security.authentication.*
import org.springframework.security.core.*
import org.springframework.security.web.authentication.*
import org.springframework.stereotype.*
import java.io.*
import java.util.*

class JWTAuthenticationFilter(
    private val authenticationManager: AuthenticationManager,
    securityProperties: SecurityProperties,
    private val jwtService: JwtService,
) : UsernamePasswordAuthenticationFilter() {

    private var objectMapper: ObjectMapper = ObjectMapper()

    companion object {

        private val LOG = LoggerFactory.getLogger(JWTAuthenticationFilter::class.java)
    }

    private val jwtProperties: SecurityProperties.JwtProperties = securityProperties.jwt

    init {
        setFilterProcessesUrl(securityProperties.loginUrl)
        LOG.info("Login URL: ${securityProperties.loginUrl}")
    }

    @Throws(AuthenticationException::class)
    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication {
        val credentials = ObjectMapper().readValue(request.inputStream, AccountCredentials::class.java)
        val authenticationToken = UsernamePasswordAuthenticationToken(
            credentials.username, credentials.password, listOf<GrantedAuthority>()
        )
        LOG.info("Attempting Authentication for ${credentials.username}")
        val authentication = authenticationManager.authenticate(authenticationToken)
        if (authentication.isAuthenticated) {
            LOG.info("Authentication successful for ${credentials.username}")
        } else {
            LOG.info("Authentication failed for ${credentials.username}")
        }
        return authentication
    }

    @Throws(IOException::class, ServletException::class)
    override fun successfulAuthentication(
        request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain, authResult: Authentication
    ) {
        LOG.info("Authentication successful")
        val token = jwtService.generateToken(authResult.principal as User)
        response.addHeader(jwtProperties.header, jwtProperties.prefix + token)
        val responseData = mapOf(
            "title" to "Authentication successful",
            "token" to token,
            "status" to HttpServletResponse.SC_OK,
        )
        val responseBody = this.objectMapper.writeValueAsString(responseData)
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.writer?.write(responseBody)
    }

    @Throws(IOException::class, ServletException::class)
    override fun unsuccessfulAuthentication(
        request: HttpServletRequest, response: HttpServletResponse, failed: AuthenticationException
    ) {
        LOG.error("Unsuccessful Authentication")
        val responseData = mapOf(
            "title" to "Authentication failed",
            "message" to failed.message,
            "timestamp" to Date().time,
            "status" to HttpServletResponse.SC_UNAUTHORIZED,
        )
        val responseBody = this.objectMapper.writeValueAsString(responseData)

        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.writer?.write(responseBody)
        response.status = HttpServletResponse.SC_UNAUTHORIZED
    }
}

