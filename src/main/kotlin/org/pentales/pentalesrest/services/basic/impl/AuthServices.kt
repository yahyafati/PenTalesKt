package org.pentales.pentalesrest.services.basic.impl

import org.pentales.pentalesrest.dto.*
import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.security.*
import org.pentales.pentalesrest.services.basic.*
import org.springframework.security.crypto.password.*
import org.springframework.stereotype.*

@Service
class AuthServices(
    private val userServices: IUserServices,
    private val passwordEncoder: PasswordEncoder,
    private val authenticationFacade: IAuthenticationFacade
) : IAuthServices {

    override fun register(registerUser: RegisterUser): User {
        val user = registerUser.toUser()
        user.password = passwordEncoder.encode(user.password)
        return userServices.save(user)
    }

    override fun getCurrentUser(): User {
        return authenticationFacade.currentUserMust
    }
}