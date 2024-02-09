package org.pentales.pentalesrest.dto.user

data class ChangePasswordDto(
    val oldPassword: String,
    val newPassword: String,
)
