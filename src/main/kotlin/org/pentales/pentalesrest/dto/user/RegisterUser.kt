package org.pentales.pentalesrest.dto.user

import jakarta.validation.constraints.*
import org.pentales.pentalesrest.models.*

data class RegisterUser(
    @field:NotBlank(message = "Username is required")
    @field:Size(min = 3, message = "Username must be at least 3 characters long")
    @field:Pattern(regexp = "^[a-zA-Z0-9]*\$", message = "Username must contain only letters and numbers")
    var username: String,
    @field:NotBlank(message = "Password is required")
    @field:Size(min = 8, message = "Password must be at least 8 characters long")
    val password: String,
    @field:NotBlank(message = "Email is required")
    @field:Email(message = "Invalid email")
    val email: String = "",
    @field:NotBlank(message = "First name is required")
    val firstName: String = "",
    @field:NotBlank(message = "Last name is required")
    val lastName: String = "",
    var displayName: String = "",
) {

    init {
        username = username.trim().lowercase()
        if (displayName.isBlank()) {
            val name = if (firstName.isBlank() && lastName.isBlank()) username else "$firstName $lastName"
            displayName = name
        }
    }

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
