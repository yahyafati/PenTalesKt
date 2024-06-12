package org.pentales.pentalesrest.models.entities.user.dto

data class ChangePasswordDto(
    val oldPassword: String,
    val newPassword: String,
)
