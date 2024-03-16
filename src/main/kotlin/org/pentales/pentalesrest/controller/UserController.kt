package org.pentales.pentalesrest.controller

import org.pentales.pentalesrest.config.security.*
import org.pentales.pentalesrest.dto.*
import org.pentales.pentalesrest.dto.user.*
import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.services.*
import org.pentales.pentalesrest.utils.*
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

    // TODO: Move the following endpoints to a separate controller, preferably FollowerController
    @GetMapping("/follow/{followerId}/followings")
    fun getFollowingsOfCurrentUser(
        @PathVariable
        followerId: Long,
    ): ResponseEntity<BasicResponseDto<List<UserDto>>> {
        val followedUser = User(id = followerId)
        val followings =
            followerServices.getFollowings(followedUser).map { UserDto(it, ServletUtil.getBaseURLFromCurrentRequest()) }
        return ResponseEntity.ok(BasicResponseDto.ok(followings))
    }

    // TODO: Move the following endpoints to a separate controller, preferably FollowerController
    @GetMapping("/follow/{followedId}/followers")
    fun getFollowersOfCurrentUser(
        @PathVariable
        followedId: Long,
    ): ResponseEntity<BasicResponseDto<List<UserDto>>> {
        val followedUser = User(id = followedId)
        val followings =
            followerServices.getFollowers(followedUser).map { UserDto(it, ServletUtil.getBaseURLFromCurrentRequest()) }
        return ResponseEntity.ok(BasicResponseDto.ok(followings))
    }

    // TODO: Move the following endpoints to a separate controller, preferably FollowerController
    @GetMapping("/follow")
    fun currentUserIsFollowing(
        @RequestParam
        username: String,
    ): ResponseEntity<BasicResponseDto<Boolean>> {
        val currentUser = authenticationFacade.forcedCurrentUser
        val following = followerServices.isFollowing(
            followerUsername = currentUser.username,
            followedUsername = username
        )
        return ResponseEntity.ok(BasicResponseDto.ok(following))
    }

    // TODO: Move the following endpoints to a separate controller, preferably FollowerController
    @PatchMapping("/follow/{followedId}/toggle")
    fun followUser(
        @PathVariable
        followedId: Long
    ): ResponseEntity<BasicResponseDto<Boolean>> {
        val currentUser = authenticationFacade.forcedCurrentUser
        val followedUser = userService.findById(followedId)
        val status = followerServices.toggleFollow(
            followerUser = currentUser,
            followedUser = followedUser
        )
        return ResponseEntity.ok(BasicResponseDto.ok(status))
    }

    @GetMapping("/is-created-using-social")
    fun isSocialRegistered(): ResponseEntity<BasicResponseDto<Boolean>> {
        val user = authenticationFacade.forcedCurrentUser
        return ResponseEntity.ok(BasicResponseDto.ok(user.password.isEmpty()))
    }

    @PostMapping("/change-password")
    fun changePassword(
        @RequestBody
        changePasswordDto: ChangePasswordDto,
    ): ResponseEntity<BasicResponseDto<Boolean>> {
        val user = authenticationFacade.forcedCurrentUser
        userService.changePassword(user, changePasswordDto)
        return ResponseEntity.ok(BasicResponseDto.ok(true))
    }

    @DeleteMapping
    fun deleteUser(): ResponseEntity<BasicResponseDto<Boolean>> {
        val user = authenticationFacade.forcedCurrentUser
        userService.deleteById(user.id)
        return ResponseEntity.ok(BasicResponseDto.ok(true))
    }
}