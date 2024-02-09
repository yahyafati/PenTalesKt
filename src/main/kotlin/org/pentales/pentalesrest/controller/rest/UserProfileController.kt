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
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.*

@RestController
@RequestMapping("/api/user/profile")
class UserProfileController(
    private val userProfileService: IUserProfileServices,
    private val authenticationFacade: IAuthenticationFacade,
) {

    @GetMapping("/followers")
    fun getFollowers(): ResponseEntity<BasicResponseDto<Page<FriendDto>>> {
        val pageParams = ServletUtil.getPageParamsFromCurrentRequest()
        val pageRequest = IBasicControllerSkeleton.getPageRequest(pageParams)
        val user = authenticationFacade.forcedCurrentUser
        val followers = userProfileService.getFollowers(user, pageRequest)
        val baseURL = ServletUtil.getBaseURLFromCurrentRequest()
        val followersDto = followers.map { FriendDto(it, baseURL) }
        return ResponseEntity.ok(BasicResponseDto.ok(followersDto))
    }

    @GetMapping("/followings")
    fun getFollowings(): ResponseEntity<BasicResponseDto<Page<FriendDto>>> {
        val user = authenticationFacade.forcedCurrentUser
        val pageParams = ServletUtil.getPageParamsFromCurrentRequest()
        val pageRequest = IBasicControllerSkeleton.getPageRequest(pageParams)
        val followings = userProfileService.getFollowings(user, pageRequest)
        val baseURL = ServletUtil.getBaseURLFromCurrentRequest()
        val followingsDto = followings.map { FriendDto(it, baseURL) }
        return ResponseEntity.ok(BasicResponseDto.ok(followingsDto))
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
}