package org.pentales.pentalesrest.security

import com.auth0.jwt.*
import com.auth0.jwt.algorithms.*
import com.fasterxml.jackson.databind.*
import jakarta.servlet.*
import jakarta.servlet.http.*
import org.pentales.pentalesrest.config.*
import org.pentales.pentalesrest.dto.*
import org.pentales.pentalesrest.models.*
import org.slf4j.*
import org.springframework.security.authentication.*
import org.springframework.security.core.*
import org.springframework.security.web.authentication.*
import org.springframework.stereotype.*
import java.io.*
import java.util.*

class JWTAuthenticationFilter(
    private val authenticationManager: AuthenticationManager,
    securityConfigProperties: SecurityConfigProperties,
    private val jwtService: JwtService,
) : UsernamePasswordAuthenticationFilter() {

    companion object {

        private val LOG = LoggerFactory.getLogger(JWTAuthenticationFilter::class.java)
    }

    private val jwtProperties: SecurityConfigProperties.JwtProperties = securityConfigProperties.jwt

    init {
        setFilterProcessesUrl(securityConfigProperties.loginUrl)
    }

    @Throws(AuthenticationException::class)
    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication {
        val credentials = ObjectMapper().readValue(request.inputStream, AccountCredentials::class.java)
        val authenticationToken = UsernamePasswordAuthenticationToken(
            credentials.username, credentials.password, listOf<GrantedAuthority>()
        )
        return authenticationManager.authenticate(authenticationToken)
    }

    @Throws(IOException::class, ServletException::class)
    override fun successfulAuthentication(
        request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain, authResult: Authentication
    ) {
        val token = jwtService.generateToken(authResult.principal as User)
        response.addHeader(jwtProperties.header, jwtProperties.prefix + token)
    }

    @Throws(IOException::class, ServletException::class)
    override fun unsuccessfulAuthentication(
        request: HttpServletRequest, response: HttpServletResponse, failed: AuthenticationException
    ) {
        LOG.error("UnSuccessful Authentication")
        response.writer.println("{\"error:\": \"Invalid Credentials\"}")
        super.unsuccessfulAuthentication(request, response, failed)
    }
}

