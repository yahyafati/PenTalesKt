package org.pentales.pentalesrest.controller

import org.pentales.pentalesrest.config.security.*
import org.pentales.pentalesrest.dto.*
import org.pentales.pentalesrest.services.*
import org.springframework.http.*
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/push-notification")
class PushNotificationController(
    private val pushNotificationService: IPushNotificationService,
    private val authenticationFacade: IAuthenticationFacade
) {

    @PostMapping
    fun registerPushNotificationToken(
        @RequestParam
        token: String
    ): ResponseEntity<BasicResponseDto<Unit>> {
        val currentUser = authenticationFacade.forcedCurrentUser
        return ResponseEntity.ok(
            BasicResponseDto.ok(pushNotificationService.registerDevice(token, currentUser))
        )
    }

    @DeleteMapping
    fun unregisterPushNotificationToken(
        @RequestParam
        token: String
    ): ResponseEntity<BasicResponseDto<Unit>> {
        return ResponseEntity.ok(
            BasicResponseDto.ok(pushNotificationService.unregisterDevice(token))
        )
    }

}
