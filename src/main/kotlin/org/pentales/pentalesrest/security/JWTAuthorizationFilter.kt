package org.pentales.pentalesrest.security

import com.auth0.jwt.exceptions.*
import jakarta.servlet.*
import jakarta.servlet.http.*
import org.pentales.pentalesrest.config.*
import org.pentales.pentalesrest.models.User
import org.pentales.pentalesrest.services.basic.*
import org.slf4j.*
import org.springframework.security.authentication.*
import org.springframework.security.core.context.*
import org.springframework.security.core.userdetails.*
import org.springframework.security.web.authentication.www.*
import org.springframework.web.filter.OncePerRequestFilter

class JWTAuthorizationFilter(
    authenticationManager: AuthenticationManager,
    securityConfigProperties: SecurityConfigProperties,
    private val userService: IUserServices,
    private val jwtService: JwtService
) : OncePerRequestFilter() {

    private var jwtProperties: SecurityConfigProperties.JwtProperties = securityConfigProperties.jwt

    companion object {

        private val LOG = LoggerFactory.getLogger(JWTAuthorizationFilter::class.java)
    }

    override fun doFilterInternal(
        request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain
    ) {
        val header = request.getHeader(jwtProperties.header)
        if (header == null || !header.startsWith(jwtProperties.prefix)) {
            LOG.warn("No JWT token found in request headers")
            throw AuthenticationCredentialsNotFoundException("No JWT token found in request headers")
//            response.status = HttpServletResponse.SC_UNAUTHORIZED
//            chain.doFilter(request, response)
        }
        val authentication = getAuthentication(request)
        if (authentication == null) {
            LOG.warn("JWT token is invalid or expired")
            response.status = HttpServletResponse.SC_UNAUTHORIZED
//            chain.doFilter(request, response)
            throw AuthenticationCredentialsNotFoundException("JWT token is invalid or expired")
        }
        LOG.info("JWT token is valid")
        SecurityContextHolder.getContext().authentication = authentication
        chain.doFilter(request, response)
    }

    private fun getAuthentication(request: HttpServletRequest): UsernamePasswordAuthenticationToken? {
        val rawToken = request.getHeader(jwtProperties.header) ?: return null

        try {
            val token = rawToken.replace(jwtProperties.prefix, "")
            val username: String = jwtService.extractUsername(token)
            LOG.info("Username: $username")
            val loggedInUser: User = userService.findByUsername(username)
            return UsernamePasswordAuthenticationToken(
                loggedInUser, null, loggedInUser.authorities
            )
        } catch (ex: JWTVerificationException) {
            LOG.error(ex.message)
        } catch (ex: UsernameNotFoundException) {
            LOG.error(ex.message)
        }
        return null
    }
}