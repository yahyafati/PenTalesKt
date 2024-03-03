package org.pentales.pentalesrest.services

import org.pentales.pentalesrest.dto.user.*
import org.pentales.pentalesrest.models.*

interface IAuthServices {

    fun register(registerUser: RegisterUser): User

    fun getCurrentUser(): User

    fun isUsernameAvailable(username: String): Boolean
}