package org.pentales.pentalesrest.config.security

import org.springframework.security.core.*
import org.springframework.security.core.context.*
import org.springframework.stereotype.*

@Component
class AuthenticationFacade : IAuthenticationFacade {

    override val authentication: Authentication?
        get() = SecurityContextHolder.getContext().authentication
}