package org.pentales.pentalesrest.services.impl

import jakarta.validation.*
import org.pentales.pentalesrest.dto.user.*
import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.security.*
import org.pentales.pentalesrest.services.*
import org.springframework.security.crypto.password.*
import org.springframework.stereotype.*
import org.springframework.validation.annotation.*

@Service
class AuthServices(
    private val userServices: IUserServices,
    private val passwordEncoder: PasswordEncoder,
    private val authenticationFacade: IAuthenticationFacade
) : IAuthServices {

    override fun register(
        @Validated
        registerUser: RegisterUser
    ): User {
        val user = registerUser.toUser()
        if (userServices.existsByUsername(user.username)) {
            throw ValidationException("Username already exists")
        }
        if (userServices.existsByEmail(user.email)) {
            throw ValidationException("Email already exists")
        }
        user.password = passwordEncoder.encode(user.password)
        return userServices.save(user)
    }

    override fun getCurrentUser(): User {
        return authenticationFacade.forcedCurrentUser
    }

    override fun isUsernameAvailable(username: String): Boolean {
        return !userServices.existsByUsername(username)
    }
}