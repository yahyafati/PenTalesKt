package org.pentales.pentalesrest.dto

import jakarta.validation.constraints.*
import org.pentales.pentalesrest.models.*

data class RegisterUser(
    @NotBlank(message = "Username is required")
    val username: String,
    @NotBlank(message = "Password is required")
    val password: String,
    @NotBlank(message = "Email is required")
    val email: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val displayName: String = "",
) {

    fun toUser(): User {
        val user = User(0L, username, password, email)
        val profile = UserProfile(firstName = firstName, lastName = lastName, displayName = displayName, user = user)
        user.profile = profile
        return user
    }

    override fun toString(): String {
        return "RegisterUser(username='$username', password='$password', email='$email', firstName='$firstName', lastName='$lastName', displayName='$displayName')"
    }
}
