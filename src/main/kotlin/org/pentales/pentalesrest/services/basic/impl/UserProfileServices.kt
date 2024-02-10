package org.pentales.pentalesrest.services.basic.impl

import org.pentales.pentalesrest.components.*
import org.pentales.pentalesrest.components.configProperties.*
import org.pentales.pentalesrest.dto.file.*
import org.pentales.pentalesrest.dto.user.*
import org.pentales.pentalesrest.exceptions.*
import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.models.User
import org.pentales.pentalesrest.repo.*
import org.pentales.pentalesrest.security.*
import org.pentales.pentalesrest.services.basic.*
import org.pentales.pentalesrest.utils.*
import org.springframework.data.domain.*
import org.springframework.security.core.userdetails.*
import org.springframework.stereotype.*
import org.springframework.transaction.annotation.*
import java.nio.file.*
import java.util.*
import kotlin.reflect.*
import kotlin.reflect.full.*

@Service
class UserProfileServices(
    private val userProfileRepository: UserProfileRepository,
    private val authenticationFacade: IAuthenticationFacade,
    private val fileService: IFileService,
    private val fileConfigProperties: FileConfigProperties,
    private val followerServices: IFollowerServices,
) : IUserProfileServices {

    companion object {

        const val MAX_FILE_NAME_LENGTH = 20
    }

    fun getUploadPath(parent: String, uploadDto: ImageUploadDto): String {
        if (uploadDto.file == null) {
            throw GenericException("File cannot be null")
        }
        val extension = FileUtil.getExtension(uploadDto.file.originalFilename ?: "").lowercase()

        val allowedExtensions = fileConfigProperties.upload.allowedExtensions
        if (extension !in allowedExtensions) {
            throw GenericException("File extension (.$extension) not allowed")
        }
        val fileName = FileUtil.getFilenameWithoutExtension(uploadDto.file.originalFilename ?: "")

        val shortFileName = if (fileName.length > MAX_FILE_NAME_LENGTH) {
            fileName.substring(0, MAX_FILE_NAME_LENGTH) + "~"
        } else {
            fileName
        }

        val uniqueFileName = shortFileName + "_" + UUID.randomUUID().toString() + "." + extension
        return Paths.get(
            fileConfigProperties.upload.path,
            parent,
            uniqueFileName
        ).toString()
    }

    fun findById(id: Long): UserProfile {
        return userProfileRepository.findById(id).orElseThrow {
            NoEntityWithIdException.create(
                "UserProfile", id
            )
        }
    }

    override fun update(profile: UpdateProfileDto, updatedFields: List<String>): UserProfile {
        val username = authenticationFacade.username ?: throw Exception("No user logged in")
        val existingEntity =
            userProfileRepository.findByUserUsername(username) ?: throw UsernameNotFoundException("No profile found")
        val entity = profile.toUserProfile()

        entity.id = existingEntity.id
        if (updatedFields.isEmpty()) {
            throw GenericException("Updated fields cannot be empty")
        }

        val modelProperties = UserProfile::class.memberProperties

        modelProperties.forEach { property ->
            if (updatedFields.contains(property.name)) {
                if (property is KMutableProperty<*>) {
                    property.setter.call(existingEntity, property.get(entity))
                }
            }
        }

        return save(existingEntity)

    }

    override fun save(profile: UserProfile): UserProfile {
        return userProfileRepository.save(profile)
    }

    @Transactional
    override fun uploadProfilePicture(userProfile: UserProfile, uploadDto: ImageUploadDto): UserProfile {
        val path = getUploadPath("profile", uploadDto)

        fileService.uploadFile(path, uploadDto.file!!.bytes)
        if (userProfile.profilePicture != null) {
            fileService.deleteFile(userProfile.profilePicture!!)
        }
        userProfileRepository.updateProfilePicture(userProfile, path)
        return findById(userProfile.id)
    }

    @Transactional
    override fun uploadProfileCover(userProfile: UserProfile, uploadDto: ImageUploadDto): UserProfile {
        val path = getUploadPath("cover", uploadDto)
        fileService.uploadFile(path, uploadDto.file!!.bytes)
        if (userProfile.coverPicture != null) {
            fileService.deleteFile(userProfile.coverPicture!!)
        }
        userProfileRepository.updateCoverPicture(userProfile, path)
        return findById(userProfile.id)
    }

    @Transactional
    override fun getFollowers(user: User, pageable: Pageable): Page<User> {
        val follower = followerServices.getFollowers(user, pageable)
        follower
            .forEach {
                it.__isFollowed = followerServices.isFollowing(authenticationFacade.forcedCurrentUser, it)
            }
        return follower
    }

    override fun getFollowings(user: User, pageable: Pageable): Page<User> {
        val followings = followerServices.getFollowings(user, pageable)
        followings.forEach {
            it.__isFollowed = true
        }
        return followings
    }

    @Transactional
    override fun deleteProfilePicture(profile: UserProfile): UserProfile {
        if (profile.profilePicture != null) {
            fileService.deleteFile(profile.profilePicture!!)
        }
        userProfileRepository.updateProfilePicture(profile, null)
        return findById(profile.id)
    }

    @Transactional
    override fun deleteProfileCover(profile: UserProfile): UserProfile {
        if (profile.coverPicture != null) {
            fileService.deleteFile(profile.coverPicture!!)
        }
        userProfileRepository.updateCoverPicture(profile, null)
        return findById(profile.id)
    }

    override fun getSuggestedFollowings(user: User): List<UserProfile> {
        val followings = followerServices.getFollowings(user)

        val suggestedFollowings = userProfileRepository.findSuggestedFollowings(followings)
        suggestedFollowings.forEach {
            it.user.__isFollowed = false
        }
        return suggestedFollowings
    }
}