package org.pentales.pentalesrest.dto

import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.models.enums.*

data class ProfileDto(
    var firstName: String = "",
    val lastName: String = "",
    var username: String = "",
    var email: String = "",
    var profilePicture: String? = "",
    var coverPicture: String? = "",
    var bio: String = "",
    var gender: EGender = EGender.FEMALE,
    var socialMedias: List<SocialMediaDto> = listOf(),
) {

    companion object {

        fun getSocialMedia(profile: UserProfile): List<SocialMediaDto> {
            val mediaMap = mapOf(
                "goodreads" to profile.goodreadsProfile,
                "twitter" to profile.twitterProfile,
                "facebook" to profile.facebookProfile,
                "instagram" to profile.instagramProfile,
                "linkedin" to profile.linkedinProfile,
                "youtube" to profile.youtubeProfile,
            )
            return mediaMap.filter { it.value != null }.map {
                SocialMediaDto(it.key, it.value!!)
            }
        }

        fun from(profile: UserProfile): ProfileDto {
            return ProfileDto(profile)
        }
    }

    constructor(profile: UserProfile) : this(
        firstName = profile.firstName,
        lastName = profile.lastName,
        username = profile.user.username,
        email = profile.user.email,
        profilePicture = profile.profilePicture,
        coverPicture = profile.coverPicture,
        bio = profile.bio,
        gender = profile.gender,
        socialMedias = getSocialMedia(profile)
    )

}