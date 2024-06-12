package org.pentales.pentalesrest.models.entities.user.auth.dto

import jakarta.validation.constraints.*

data class ForgotPasswordDto(
    @field:NotBlank(message = "Email is required")
    @field:Email(message = "Email is not valid")
    val email: String,
    val callbackURL: String
)
