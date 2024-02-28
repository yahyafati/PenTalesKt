package org.pentales.pentalesrest.controller.rest

import org.pentales.pentalesrest.dto.*
import org.pentales.pentalesrest.dto.file.*
import org.pentales.pentalesrest.dto.user.*
import org.pentales.pentalesrest.exceptions.*
import org.pentales.pentalesrest.security.*
import org.pentales.pentalesrest.services.basic.*
import org.pentales.pentalesrest.utils.*
import org.springframework.data.domain.*
import org.springframework.http.*
import org.springframework.validation.annotation.*
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.*

@RestController
@RequestMapping("/api/user/profile")
class UserProfileController(
    private val userProfileService: IUserProfileServices,
    private val authenticationFacade: IAuthenticationFacade,
) {

    @GetMapping
    fun getProfile(
        @RequestParam(required = false)
        username: String?,
    ): ResponseEntity<BasicResponseDto<ProfileDto>> {
        val user = authenticationFacade.forcedCurrentUser
        val profile = if (username != null) {
            userProfileService.getProfileByUsername(username)
        } else {
            user.profile ?: throw GenericException("No profile found")
        }
        val baseURL = ServletUtil.getBaseURLFromCurrentRequest()
        return ResponseEntity.ok(BasicResponseDto.ok(ProfileDto(profile, baseURL)))
    }

    @GetMapping("/meta")
    fun getProfileMeta(
        @RequestParam(required = false)
        username: String?,
    ): ResponseEntity<BasicResponseDto<ProfileMetaDto>> {
        val user = authenticationFacade.forcedCurrentUser
        val profileMeta: ProfileMetaDto = userProfileService.getProfileMeta(username ?: user.username)
        return ResponseEntity.ok(BasicResponseDto.ok(profileMeta))
    }

    @PutMapping("")
    fun updateProfile(
        @RequestBody
        @Validated
        profile: UpdateProfileDto,
        @RequestParam
        updateFields: List<String>?,
    ): ResponseEntity<BasicResponseDto<UpdateProfileDto>> {
        val updatedProfile = userProfileService.update(profile, updateFields ?: listOf())
        return ResponseEntity.ok(BasicResponseDto.ok(UpdateProfileDto(updatedProfile)))
    }

    @PostMapping("/search-friends")
    fun searchFriends(
        @RequestBody(required = false)
        filters: List<FilterDto> = emptyList(),
    ): ResponseEntity<BasicResponseDto<Page<FriendDto>>> {
        val user = authenticationFacade.forcedCurrentUser
        val pageRequest = ServletUtil.getPageRequest()
        val friends = userProfileService.searchFriends(user, filters, pageRequest)
        val baseURL = ServletUtil.getBaseURLFromCurrentRequest()
        val friendsDto = friends.map { FriendDto(it.user, baseURL) }
        return ResponseEntity.ok(BasicResponseDto.ok(friendsDto))
    }

    @GetMapping("/followers")
    fun getFollowers(): ResponseEntity<BasicResponseDto<Page<FriendDto>>> {
        val pageRequest = ServletUtil.getPageRequest()
        val user = authenticationFacade.forcedCurrentUser
        val followers = userProfileService.getFollowers(user, pageRequest)
        val baseURL = ServletUtil.getBaseURLFromCurrentRequest()
        val followersDto = followers.map { FriendDto(it, baseURL) }
        return ResponseEntity.ok(BasicResponseDto.ok(followersDto))
    }

    @GetMapping("/followings")
    fun getFollowings(): ResponseEntity<BasicResponseDto<Page<FriendDto>>> {
        val user = authenticationFacade.forcedCurrentUser
        val pageRequest = ServletUtil.getPageRequest()
        val followings = userProfileService.getFollowings(user, pageRequest)
        val baseURL = ServletUtil.getBaseURLFromCurrentRequest()
        val followingsDto = followings.map { FriendDto(it, baseURL) }
        return ResponseEntity.ok(BasicResponseDto.ok(followingsDto))
    }

    @GetMapping("/suggested-users")
    fun getSuggestedFollowings(): ResponseEntity<BasicResponseDto<List<FriendDto>>> {
        val user = authenticationFacade.forcedCurrentUser
        val suggestedFollowings = userProfileService.getSuggestedFollowings(user)
        val baseURL = ServletUtil.getBaseURLFromCurrentRequest()
        val suggestedFollowingsDto = suggestedFollowings.map { FriendDto(it.user, baseURL) }
        return ResponseEntity.ok(BasicResponseDto.ok(suggestedFollowingsDto))
    }

    @PostMapping(
        "/picture",
        consumes = [MediaType.MULTIPART_FORM_DATA_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun uploadProfilePicture(
        @RequestBody
        file: MultipartFile,
    ): ResponseEntity<BasicResponseDto<ProfileDto>> {
        val user = authenticationFacade.forcedCurrentUser
        val imageUploadDto = ImageUploadDto(file = file)
        val profile = user.profile ?: throw GenericException("No profile found")
        val updatedProfile = userProfileService.uploadProfilePicture(profile, imageUploadDto)
        val baseURL = ServletUtil.getBaseURLFromCurrentRequest()
        return ResponseEntity.ok(BasicResponseDto.ok(ProfileDto(updatedProfile, baseURL)))
    }

    @DeleteMapping("/picture")
    fun deleteProfilePicture(): ResponseEntity<BasicResponseDto<ProfileDto>> {
        val user = authenticationFacade.forcedCurrentUser
        val profile = user.profile ?: throw GenericException("No profile found")
        val updatedProfile = userProfileService.deleteProfilePicture(profile)
        val baseURL = ServletUtil.getBaseURLFromCurrentRequest()
        return ResponseEntity.ok(BasicResponseDto.ok(ProfileDto(updatedProfile, baseURL)))
    }

    @PostMapping(
        "/cover",
        consumes = [MediaType.MULTIPART_FORM_DATA_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun uploadProfileCover(
        @RequestBody
        file: MultipartFile,
    ): ResponseEntity<BasicResponseDto<ProfileDto>> {
        val user = authenticationFacade.forcedCurrentUser
        val imageUploadDto = ImageUploadDto(file = file)
        val profile = user.profile ?: throw GenericException("No profile found")
        val updatedProfile = userProfileService.uploadProfileCover(profile, imageUploadDto)
        val baseURL = ServletUtil.getBaseURLFromCurrentRequest()
        return ResponseEntity.ok(BasicResponseDto.ok(ProfileDto(updatedProfile, baseURL)))
    }

    @DeleteMapping("/cover")
    fun deleteProfileCover(): ResponseEntity<BasicResponseDto<ProfileDto>> {
        val user = authenticationFacade.forcedCurrentUser
        val profile = user.profile ?: throw GenericException("No profile found")
        val updatedProfile = userProfileService.deleteProfileCover(profile)
        val baseURL = ServletUtil.getBaseURLFromCurrentRequest()
        return ResponseEntity.ok(BasicResponseDto.ok(ProfileDto(updatedProfile, baseURL)))
    }
}