package org.pentales.pentalesrest.services.impl

import org.pentales.pentalesrest.dto.*
import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.services.*
import org.springframework.security.crypto.password.*
import org.springframework.stereotype.*

@Service
class AuthServices(
    private val userServices: IUserService, private val passwordEncoder: PasswordEncoder
) : IAuthServices {


    override fun register(registerUser: RegisterUser): User {
        val user = registerUser.toUser()
        user.password = passwordEncoder.encode(user.password)
        return userServices.save(user)
    }
}