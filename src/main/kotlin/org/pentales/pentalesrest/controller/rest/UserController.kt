package org.pentales.pentalesrest.controller.rest

import org.pentales.pentalesrest.dto.*
import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.services.basic.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/user")
class UserController(
    private val userService: IUserServices,
    private val userProfileService: IUserProfileServices,
) {

    @PutMapping("/profile")
    fun updateProfile(
        @RequestBody profile: UpdateProfileDto,
        @RequestParam updateFields: List<String>?,
    ): ResponseEntity<UserProfile> {
        return ResponseEntity.ok(userProfileService.update(profile, updateFields ?: listOf()))
    }

}