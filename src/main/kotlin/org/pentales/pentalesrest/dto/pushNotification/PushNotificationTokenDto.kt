package org.pentales.pentalesrest.dto.pushNotification

import jakarta.validation.constraints.*

data class PushNotificationTokenDto(
    @field:NotBlank(message = "Token is required")
    val token: String,
)