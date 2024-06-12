package org.pentales.pentalesrest.models.entities.user.auth

import org.pentales.pentalesrest.models.entities.user.*
import org.pentales.pentalesrest.models.entities.user.dto.*

interface IAuthServices {

    fun register(registerUser: RegisterUser): User

    fun getCurrentUser(): User

    fun isUsernameAvailable(username: String): Boolean
}