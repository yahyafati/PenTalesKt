package org.pentales.pentalesrest.dto

import org.pentales.pentalesrest.models.*

data class UserDto(
    val id: Long = 0,
    val username: String = "",
    val email: String = "",
    val firstName: String? = null,
    val lastName: String? = null,
    val profilePicture: String? = null,
) {

    constructor(user: User) : this(
        id = user.id,
        username = user.username,
        email = user.email,
        firstName = user.profile?.firstName,
        lastName = user.profile?.lastName,
        profilePicture = user.profile?.profilePicture
    )

    fun toUser(): User {
        val user = User(
            id = id,
            username = username,
            email = email,
            profile = UserProfile(
                firstName = firstName ?: "",
                lastName = lastName ?: "",
                profilePicture = profilePicture,
            ),
        )
        user.id = id
        return user
    }
}