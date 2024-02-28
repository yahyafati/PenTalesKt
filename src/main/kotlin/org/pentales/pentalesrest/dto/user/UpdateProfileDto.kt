package org.pentales.pentalesrest.dto.user

import jakarta.validation.constraints.*
import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.models.enums.*
import java.time.*

data class UpdateProfileDto(
    @field:NotBlank(message = "First name is required")
    var firstName: String = "",
    @field:NotBlank(message = "Last name is required")
    var lastName: String = "",
    @field:NotBlank(message = "Display name is required")
    var displayName: String = "",
    var bio: String? = "",
    @field:NotNull
    var gender: EGender = EGender.MALE,
    @field:NotNull
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

    fun toUserProfile(): UserProfile {
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