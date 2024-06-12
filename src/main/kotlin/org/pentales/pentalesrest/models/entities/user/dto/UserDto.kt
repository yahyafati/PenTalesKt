package org.pentales.pentalesrest.models.entities.user.dto

import com.fasterxml.jackson.annotation.*
import org.pentales.pentalesrest.models.entities.user.*
import org.pentales.pentalesrest.models.entities.user.profile.*

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

        fun getURLWithBaseURL(url: String?, baseURL: String): String? {
            if (url == null) return null
            if (url.startsWith("http")) return url
            return "$baseURL/api/assets/$url"
        }

        fun getURLWithoutBaseURL(url: String?, userDto: UserDto): String? {
            val baseURL = userDto.baseURL
            return url?.removePrefix("$baseURL/api/assets/")
        }
    }

    constructor(user: User, baseURL: String) : this(
        id = user.id,
        username = user.username,
        email = user.email,
        firstName = user.profile?.firstName,
        lastName = user.profile?.lastName,
        profilePicture = getURLWithBaseURL(user.profile?.profilePicture, baseURL),
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
                profilePicture = getURLWithoutBaseURL(profilePicture, this)
            ),
        )
        user.id = id
        return user
    }

    override fun toString(): String {
        return "UserDto(id=$id, username='$username', email='$email', firstName=$firstName, lastName=$lastName, profilePicture=$profilePicture, isFollowed=$isFollowed, baseURL='$baseURL')"
    }

}