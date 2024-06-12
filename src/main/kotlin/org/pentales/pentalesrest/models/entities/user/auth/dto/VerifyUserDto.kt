package org.pentales.pentalesrest.models.entities.user.auth.dto

import jakarta.validation.constraints.*

data class VerifyUserDto(
    @field:NotBlank(message = "Email is required")
    @field:Email(message = "Invalid email")
    val email: String,
    @field:NotBlank(message = "Code is required")
    val code: String,
)
