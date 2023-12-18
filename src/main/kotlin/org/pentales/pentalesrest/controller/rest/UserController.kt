package org.pentales.pentalesrest.controller.rest

import org.pentales.pentalesrest.dto.*
import org.pentales.pentalesrest.security.*
import org.pentales.pentalesrest.services.basic.*
import org.springframework.http.*
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/user")
class UserController(
    private val userService: IUserServices,
    private val userProfileService: IUserProfileServices,
    private val userGoalServices: IUserGoalServices,
    private val authenticationFacade: IAuthenticationFacade,
) {

    @PutMapping("/profile")
    fun updateProfile(
        @RequestBody
        profile: UpdateProfileDto,
        @RequestParam
        updateFields: List<String>?,
    ): ResponseEntity<BasicResponseDto<UpdateProfileDto>> {
        val updatedProfile = userProfileService.update(profile, updateFields ?: listOf())
        return ResponseEntity.ok(BasicResponseDto.ok(UpdateProfileDto(updatedProfile)))
    }

    @PatchMapping("/target")
    fun updateGoalTarget(
        @RequestBody
        userGoalDto: UserGoalDto,
    ): ResponseEntity<BasicResponseDto<UserGoalDto>> {
        val userId = authenticationFacade.forcedCurrentUser.id
        val userGoal = userGoalServices.setYearsGoal(
            userId = userId, target = userGoalDto.target, year = userGoalDto.year
        )
        return ResponseEntity.ok(BasicResponseDto.ok(UserGoalDto(userGoal)))
    }

}