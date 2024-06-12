package org.pentales.pentalesrest.config.security

import com.auth0.jwt.exceptions.*
import com.fasterxml.jackson.databind.*
import jakarta.servlet.*
import jakarta.servlet.http.*
import org.pentales.pentalesrest.config.properties.*
import org.pentales.pentalesrest.exceptions.*
import org.pentales.pentalesrest.models.entities.user.*
import org.pentales.pentalesrest.models.entities.user.User
import org.pentales.pentalesrest.models.misc.jwt.*
import org.slf4j.*
import org.springframework.http.*
import org.springframework.security.authentication.*
import org.springframework.security.core.context.*
import org.springframework.security.core.userdetails.*
import org.springframework.web.filter.*

class JWTAuthorizationFilter(
    securityProperties: SecurityProperties,
    private val userService: IUserServices,
    private val jwtService: JwtService
) : OncePerRequestFilter() {

    private var jwtProperties: SecurityProperties.JwtProperties = securityProperties.jwt

    companion object {

        private val LOG = LoggerFactory.getLogger(JWTAuthorizationFilter::class.java)
    }

    override fun doFilterInternal(
        request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain
    ) {
        val header = request.getHeader(jwtProperties.header)
        if (header == null || !header.startsWith(jwtProperties.prefix)) {
            LOG.debug("No JWT token found in request headers")
            chain.doFilter(request, response)
            return
        }
        val authentication = try {
            getAuthentication(request)
        } catch (ex: JWTVerificationException) {
            respondUnauthorized(response, ex)
            return
        } catch (ex: UsernameNotFoundException) {
            respondUnauthorized(response, ex)
            return
        }
        SecurityContextHolder.getContext().authentication = authentication
        chain.doFilter(request, response)
    }

    private fun respondUnauthorized(response: HttpServletResponse, ex: Exception) {
        val objectMapper = ObjectMapper()
        val errorModel = GenericErrorModel(
            message = ex.message ?: "Unauthorized",
            timestamp = System.currentTimeMillis(),
            status = HttpStatus.UNAUTHORIZED.value(),
            exception = ex,
        )
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.status = HttpStatus.UNAUTHORIZED.value()
        val outputStream = response.outputStream
        objectMapper.writeValue(outputStream, errorModel)
        outputStream.flush()
    }

    private fun getAuthentication(request: HttpServletRequest): UsernamePasswordAuthenticationToken? {
        val rawToken = request.getHeader(jwtProperties.header) ?: return null

        try {
            val token = rawToken.replace(jwtProperties.prefix, "")
            val username: String = jwtService.extractUsername(token)
            val loggedInUser: User = userService.findByUsername(username)
            return UsernamePasswordAuthenticationToken(
                loggedInUser, null, loggedInUser.authorities.map { it.authority }
            )
        } catch (ex: JWTVerificationException) {
            LOG.error(ex.message)
            throw ex
        } catch (ex: UsernameNotFoundException) {
            LOG.error(ex.message)
            throw ex
        }
    }
}