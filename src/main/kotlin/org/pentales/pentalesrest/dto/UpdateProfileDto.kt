package org.pentales.pentalesrest.dto

import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.models.enums.*
import java.time.LocalDate

data class UpdateProfileDto(
    var firstName: String = "",
    var lastName: String = "",
    var displayName: String = "",
    var bio: String = "",
    var gender: EGender = EGender.MALE,
    var dateOfBirth: LocalDate = LocalDate.now(),
) {

    constructor(profile: UserProfile) : this(
        firstName = profile.firstName,
        lastName = profile.lastName,
        displayName = profile.displayName,
        bio = profile.bio,
        gender = profile.gender,
        dateOfBirth = profile.dateOfBirth,
    )

    fun toUserProfile() : UserProfile {
        return UserProfile(
            firstName = firstName,
            lastName = lastName,
            displayName = displayName,
            bio = bio,
            gender = gender,
            dateOfBirth = dateOfBirth,
        )
    }
}