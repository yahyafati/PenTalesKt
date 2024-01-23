package org.pentales.pentalesrest.controller.rest

import org.pentales.pentalesrest.dto.*
import org.pentales.pentalesrest.dto.user.*
import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.security.*
import org.pentales.pentalesrest.services.basic.*
import org.springframework.http.*
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/user")
class UserController(
    private val userService: IUserServices,
    private val userProfileService: IUserProfileServices,
    private val followerServices: IFollowerServices,
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

    @GetMapping("/follow/{followerId}/followings")
    fun getFollowingsOfCurrentUser(
        @PathVariable
        followerId: Long
    ): ResponseEntity<BasicResponseDto<List<UserDto>>> {
        val followedUser = User(id = followerId)
        val followings = followerServices.getFollowings(followedUser).map { UserDto(it) }
        return ResponseEntity.ok(BasicResponseDto.ok(followings))
    }

    @GetMapping("/follow/{followedId}/followers")
    fun getFollowersOfCurrentUser(
        @PathVariable
        followedId: Long
    ): ResponseEntity<BasicResponseDto<List<UserDto>>> {
        val followedUser = User(id = followedId)
        val followings = followerServices.getFollowers(followedUser).map { UserDto(it) }
        return ResponseEntity.ok(BasicResponseDto.ok(followings))
    }

    @GetMapping("/follow/{followedId}")
    fun currentUserIsFollowing(
        @PathVariable
        followedId: Long
    ): ResponseEntity<BasicResponseDto<Boolean>> {
        val currentUserId = authenticationFacade.currentUserId
        val following = followerServices.isFollowing(
            follower = User(id = currentUserId),
            followed = User(id = followedId),
        )
        return ResponseEntity.ok(BasicResponseDto.ok(following))
    }

    @PatchMapping("/follow/{followedId}/toggle")
    fun followUser(
        @PathVariable
        followedId: Long
    ): ResponseEntity<BasicResponseDto<Boolean>> {
        val currentUserId = authenticationFacade.currentUserId
        val status = followerServices.toggleFollow(
            followerUser = User(id = currentUserId),
            followedUser = User(id = followedId),
        )
        return ResponseEntity.ok(BasicResponseDto.ok(status))
    }

}