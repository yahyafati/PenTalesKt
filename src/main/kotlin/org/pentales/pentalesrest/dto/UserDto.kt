package org.pentales.pentalesrest.dto

import org.pentales.pentalesrest.models.*

data class UserDto(
    val username: String,
    val email: String,
) {


    constructor(user: User) : this(user.username, user.email)
}