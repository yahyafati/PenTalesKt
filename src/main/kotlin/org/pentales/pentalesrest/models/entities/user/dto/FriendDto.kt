package org.pentales.pentalesrest.models.entities.user.dto

import org.pentales.pentalesrest.models.entities.user.*

data class FriendDto(
    val id: Long,
    val username: String,
    val firstName: String,
    val lastName: String,
    val profilePicture: String?,
    val bio: String,
    val isFollowed: Boolean = false
) {

    constructor(user: User, baseURL: String) : this(
        id = user.id,
        username = user.username,
        firstName = user.profile?.firstName ?: "",
        lastName = user.profile?.lastName ?: "",
        profilePicture = UserDto.getURLWithBaseURL(user.profile?.profilePicture, baseURL),
        bio = user.profile?.bio ?: "",
        isFollowed = user.__isFollowed
    )
}
