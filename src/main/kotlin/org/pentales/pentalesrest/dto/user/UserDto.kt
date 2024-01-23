package org.pentales.pentalesrest.dto.user

import org.pentales.pentalesrest.models.*

data class UserDto(
    val id: Long = 0,
    val username: String = "",
    val email: String = "",
    val firstName: String? = null,
    val lastName: String? = null,
    val profilePicture: String? = null,
    val isFollowed: Boolean = false,
) {

    constructor(user: User) : this(
        id = user.id,
        username = user.username,
        email = user.email,
        firstName = user.profile?.firstName,
        lastName = user.profile?.lastName,
        profilePicture = user.profile?.profilePicture,
        isFollowed = user.__isFollowed,
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