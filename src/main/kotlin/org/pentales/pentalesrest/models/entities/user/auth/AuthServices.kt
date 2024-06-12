package org.pentales.pentalesrest.models.entities.user.auth

import jakarta.validation.*
import org.pentales.pentalesrest.config.security.*
import org.pentales.pentalesrest.models.entities.user.*
import org.pentales.pentalesrest.models.entities.user.dto.*
import org.pentales.pentalesrest.utils.*
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

        val profile = user.profile
        if (profile != null && profile.profilePicture.isNullOrBlank()) {
            profile.profilePicture = UserUtils.getProfileAvatar(profile.firstName, profile.lastName)
        }
        return userServices.save(user)
    }

    override fun getCurrentUser(): User {
        return authenticationFacade.forcedCurrentUser
    }

    override fun isUsernameAvailable(username: String): Boolean {
        return !userServices.existsByUsername(username)
    }
}