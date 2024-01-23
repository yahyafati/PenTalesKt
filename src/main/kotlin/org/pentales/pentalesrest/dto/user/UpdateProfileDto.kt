package org.pentales.pentalesrest.dto.user

import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.models.enums.*
import java.time.*

data class UpdateProfileDto(
    var firstName: String = "",
    var lastName: String = "",
    var displayName: String = "",
    var bio: String = "",
    var gender: EGender = EGender.MALE,
    var dateOfBirth: LocalDate = LocalDate.now(),
    var goodreadsProfile: String = "",
    var twitterProfile: String = "",
    var facebookProfile: String = "",
    var instagramProfile: String = "",
    var linkedinProfile: String = "",
    var youtubeProfile: String = ""
) {

    constructor(profile: UserProfile) : this(
        firstName = profile.firstName,
        lastName = profile.lastName,
        displayName = profile.displayName,
        bio = profile.bio,
        gender = profile.gender,
        dateOfBirth = profile.dateOfBirth,
        goodreadsProfile = profile.goodreadsProfile ?: "",
        twitterProfile = profile.twitterProfile ?: "",
        facebookProfile = profile.facebookProfile ?: "",
        instagramProfile = profile.instagramProfile ?: "",
        linkedinProfile = profile.linkedinProfile ?: "",
        youtubeProfile = profile.youtubeProfile ?: ""
    )

    fun toUserProfile(): UserProfile {
        return UserProfile(
            firstName = firstName,
            lastName = lastName,
            displayName = displayName,
            bio = bio,
            gender = gender,
            dateOfBirth = dateOfBirth,
            goodreadsProfile = goodreadsProfile,
            twitterProfile = twitterProfile,
            facebookProfile = facebookProfile,
            instagramProfile = instagramProfile,
            linkedinProfile = linkedinProfile,
            youtubeProfile = youtubeProfile
        )
    }
}