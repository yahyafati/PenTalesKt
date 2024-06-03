package org.pentales.pentalesrest.services.impl

import jakarta.validation.*
import org.pentales.pentalesrest.config.security.*
import org.pentales.pentalesrest.dto.user.*
import org.pentales.pentalesrest.models.*
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

    private val backgroundForegroundCombos = listOf(
        "f44336" to "fff",
        "e91e63" to "fff",
        "2cb422" to "504848",
        "2196f3" to "000000",
        "ff9800" to "60a0a6",
        "9c27b0" to "fff",
        "3f51b5" to "fff",
        "009688" to "fff",
        "ff5722" to "fff",
        "607d8b" to "fff",
        "f44336" to "fff",
        "e91e63" to "fff",
    )

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
        if (profile != null) {
            if (profile.profilePicture.isNullOrBlank()) {
                val (background, foreground) = backgroundForegroundCombos.random()
                profile.profilePicture =
                    "https://ui-avatars.com/api/?name=${profile.firstName}+${profile.lastName}&background=$background&color=$foreground"
            }
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