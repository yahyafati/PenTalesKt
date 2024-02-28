package org.pentales.pentalesrest.dto.user

import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.models.enums.*

data class ProfileDto(
    var userId: Long = 0L,
    var firstName: String = "",
    val lastName: String = "",
    var username: String = "",
    var email: String = "",
    var profilePicture: String? = "",
    var coverPicture: String? = "",
    var bio: String? = "",
    var gender: EGender = EGender.FEMALE,
) {

    constructor(profile: UserProfile, baseURL: String) : this(
        userId = profile.user.id,
        firstName = profile.firstName,
        lastName = profile.lastName,
        username = profile.user.username,
        email = profile.user.email,
        profilePicture = UserDto.getURLWithBaseURL(profile.profilePicture, baseURL),
        coverPicture = UserDto.getURLWithBaseURL(profile.coverPicture, baseURL),
        bio = profile.bio,
        gender = profile.gender,
    )

}