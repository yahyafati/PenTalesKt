package org.pentales.pentalesrest.dto.user

import com.fasterxml.jackson.annotation.*
import org.pentales.pentalesrest.models.*

class UserDto(
    val id: Long = 0,
    val username: String = "",
    val email: String = "",
    val firstName: String? = null,
    val lastName: String? = null,
    val profilePicture: String? = null,
    val isFollowed: Boolean = false,
    @JsonIgnore
    private val baseURL: String = "http://localhost:8080"
) {

    companion object {

        fun getProfilePictureWithBaseURL(profile: UserProfile?, baseURL: String): String? {
            val profilePicture = profile?.profilePicture ?: return null
            return "$baseURL/api/assets/$profilePicture"
        }

        fun getProfilePictureWithoutBaseURL(userDto: UserDto): String? {
            val baseURL = userDto.baseURL
            val profilePicture = userDto.profilePicture ?: return null
            return profilePicture.removePrefix("$baseURL/api/assets/")
        }
    }

    constructor(user: User, baseURL: String) : this(
        id = user.id,
        username = user.username,
        email = user.email,
        firstName = user.profile?.firstName,
        lastName = user.profile?.lastName,
        profilePicture = getProfilePictureWithBaseURL(user.profile, baseURL),
        isFollowed = user.__isFollowed,
        baseURL = baseURL
    )

    fun toUser(): User {
        val user = User(
            id = id,
            username = username,
            email = email,
            profile = UserProfile(
                firstName = firstName ?: "",
                lastName = lastName ?: "",
                profilePicture = getProfilePictureWithoutBaseURL(this)
            ),
        )
        user.id = id
        return user
    }

    override fun toString(): String {
        return "UserDto(id=$id, username='$username', email='$email', firstName=$firstName, lastName=$lastName, profilePicture=$profilePicture, isFollowed=$isFollowed, baseURL='$baseURL')"
    }

}