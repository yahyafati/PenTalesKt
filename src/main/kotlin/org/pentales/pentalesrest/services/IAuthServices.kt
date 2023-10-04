package org.pentales.pentalesrest.services

import org.pentales.pentalesrest.dto.*
import org.pentales.pentalesrest.models.*

interface IAuthServices {


    fun register(registerUser: RegisterUser): User
}