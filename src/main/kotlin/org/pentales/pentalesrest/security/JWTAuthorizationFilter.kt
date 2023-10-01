package org.pentales.pentalesrest.security

import com.auth0.jwt.exceptions.*
import jakarta.servlet.*
import jakarta.servlet.http.*
import org.pentales.pentalesrest.config.*
import org.pentales.pentalesrest.models.User
import org.pentales.pentalesrest.services.*
import org.slf4j.*
import org.springframework.security.authentication.*
import org.springframework.security.core.context.*
import org.springframework.security.core.userdetails.*
import org.springframework.security.web.authentication.www.*

class JWTAuthorizationFilter(
    authenticationManager: AuthenticationManager,
    securityConfigProperties: SecurityConfigProperties,
    private val userService: IUserService,
    private val jwtService: JwtService
) : BasicAuthenticationFilter(authenticationManager) {

    private var jwtProperties: SecurityConfigProperties.JwtProperties = securityConfigProperties.jwt

    companion object {

        private val LOG = LoggerFactory.getLogger(JWTAuthorizationFilter::class.java)
    }

    override fun doFilterInternal(
        request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain
    ) {
        val header = request.getHeader(jwtProperties.header)
        if (header == null || !header.startsWith(jwtProperties.prefix)) {
            chain.doFilter(request, response)
            return
        }
        val authentication = getAuthentication(request)
        SecurityContextHolder.getContext().authentication = authentication
        chain.doFilter(request, response)
    }

    private fun getAuthentication(request: HttpServletRequest): UsernamePasswordAuthenticationToken? {
        val rawToken = request.getHeader(jwtProperties.header) ?: return null

        try {
            val token = rawToken.replace(jwtProperties.prefix, "")
            val username: String = jwtService.extractUsername(token)
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