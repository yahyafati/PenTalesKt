package org.pentales.pentalesrest.dto.user

import org.pentales.pentalesrest.models.*

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
        profilePicture = UserDto.getProfilePictureWithBaseURL(user.profile, baseURL),
        bio = user.profile?.bio ?: "",
        isFollowed = user.__isFollowed
    )
}
