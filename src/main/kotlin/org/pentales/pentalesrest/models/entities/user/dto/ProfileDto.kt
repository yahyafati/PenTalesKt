package org.pentales.pentalesrest.models.entities.user.dto

import org.pentales.pentalesrest.models.entities.user.profile.*

data class ProfileDto(
    var userId: Long = 0L,
    var firstName: String = "",
    val lastName: String = "",
    val displayName: String = "",
    var username: String = "",
    var email: String = "",
    var profilePicture: String? = "",
    var coverPicture: String? = "",
    var bio: String? = "",
    var gender: EGender = EGender.FEMALE,
    var dateOfBirth: String? = "",
) {

    constructor(profile: UserProfile, baseURL: String) : this(
        userId = profile.user.id,
        firstName = profile.firstName,
        lastName = profile.lastName,
        username = profile.user.username,
        displayName = profile.displayName,
        email = profile.user.email,
        profilePicture = UserDto.getURLWithBaseURL(profile.profilePicture, baseURL),
        coverPicture = UserDto.getURLWithBaseURL(profile.coverPicture, baseURL),
        bio = profile.bio,
        gender = profile.gender,
        dateOfBirth = profile.dateOfBirth.toString(),
    )

}