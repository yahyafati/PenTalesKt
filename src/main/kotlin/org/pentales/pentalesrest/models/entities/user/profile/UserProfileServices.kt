package org.pentales.pentalesrest.models.entities.user.profile

import net.coobird.thumbnailator.*
import net.coobird.thumbnailator.geometry.*
import net.coobird.thumbnailator.util.*
import org.pentales.pentalesrest.config.properties.*
import org.pentales.pentalesrest.config.security.*
import org.pentales.pentalesrest.exceptions.*
import org.pentales.pentalesrest.global.components.*
import org.pentales.pentalesrest.global.dto.*
import org.pentales.pentalesrest.global.dto.file.*
import org.pentales.pentalesrest.models.entities.rating.*
import org.pentales.pentalesrest.models.entities.user.*
import org.pentales.pentalesrest.models.entities.user.User
import org.pentales.pentalesrest.models.entities.user.dto.*
import org.pentales.pentalesrest.models.entities.user.follower.*
import org.pentales.pentalesrest.models.misc.asset.*
import org.pentales.pentalesrest.utils.*
import org.springframework.data.domain.*
import org.springframework.security.core.userdetails.*
import org.springframework.stereotype.*
import org.springframework.transaction.annotation.*
import java.nio.file.*
import kotlin.reflect.*
import kotlin.reflect.full.*

@Service
class UserProfileServices(
    private val userProfileRepository: UserProfileRepository,
    private val userRepository: UserRepository,
    private val authenticationFacade: IAuthenticationFacade,
    private val fileService: IFileService,
    private val assetProperties: AssetProperties,
    private val followerServices: IFollowerServices,
    private val ratingRepository: RatingRepository,
    private val superAdminProvider: SuperAdminProvider,
    private val userProfileSpecification: UserProfileSpecification,
) : IUserProfileServices {

    companion object {

        const val MAX_SUGGESTED_FOLLOWINGS = 5
    }

    fun getUploadPath(parent: String, originalFilename: String): String {
        val extension = FileUtil.getExtension(originalFilename).lowercase()

        val allowedExtensions = assetProperties.upload.allowedExtensions
        if (extension !in allowedExtensions) {
            throw GenericException("File extension (.$extension) not allowed")
        }
        val fileName = FileUtil.getFilenameWithoutExtension(originalFilename)

        val uniqueFileName = FileUtil.getUniqueFilename(fileName, extension)
        return Paths.get(
            assetProperties.upload.path,
            parent,
            uniqueFileName
        ).toString()
    }

    fun getUploadPath(parent: String, uploadDto: ImageUploadDto): String {
        if (uploadDto.file == null) {
            throw GenericException("File cannot be null")
        }

        return getUploadPath(parent, uploadDto.file.originalFilename ?: "")
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
        val file = uploadDto.file ?: throw GenericException("File cannot be null")
        val extension = FileUtil.getExtension(file.originalFilename ?: "").lowercase()

        val isExtensionAllowed = ThumbnailatorUtils.isSupportedOutputFormat(extension)

        val scaledFile = Thumbnails.of(file.inputStream)
            .size(300, 300)
            .crop(Positions.CENTER)
            .outputFormat(
                if (isExtensionAllowed) {
                    "jpg"
                } else {
                    ThumbnailParameter.ORIGINAL_FORMAT
                }
            )
            .asBufferedImage()

        val byteArray = FileUtil.toByteArray(scaledFile, "jpg")
        return uploadProfilePicture(userProfile, byteArray, file.originalFilename ?: "")
    }

    override fun uploadProfilePicture(
        userProfile: UserProfile,
        byteArray: ByteArray,
        originalFilename: String
    ): UserProfile {
        val path = getUploadPath("profile", originalFilename)

        fileService.uploadFile(path, byteArray)

        if (userProfile.profilePicture != null) {
            fileService.deleteFile(userProfile.profilePicture!!)
        }

        userProfileRepository.updateProfilePicture(userProfile, path)
        return findById(userProfile.id)
    }

    @Transactional
    override fun uploadProfileCover(userProfile: UserProfile, uploadDto: ImageUploadDto): UserProfile {
        val path = getUploadPath("cover", uploadDto)
        val scaledFile = Thumbnails.of(uploadDto.file!!.inputStream)
            .size(1200, 300)
            .crop(Positions.CENTER)
            .keepAspectRatio(true) // This is the default, but it doesn't hurt to be explicit
            .outputFormat("jpg")
            .asBufferedImage()
        val byteArray = FileUtil.toByteArray(scaledFile, "jpg")
        fileService.uploadFile(path, byteArray)
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

    @Transactional
    override fun getFollowers(username: String, pageable: Pageable): Page<User> {
        val user = userRepository.findByUsername(username) ?: throw NoEntityWithIdException.create("User", username)
        return getFollowers(user, pageable)
    }

    override fun getFollowings(user: User, pageable: Pageable): Page<User> {
        val followings = followerServices.getFollowings(user, pageable)
        followings.forEach {
            it.__isFollowed = true
        }
        return followings
    }

    override fun getFollowings(username: String, pageable: Pageable): Page<User> {
        val user = userRepository.findByUsername(username) ?: throw NoEntityWithIdException.create("User", username)
        return getFollowings(user, pageable)
    }

    @Transactional
    override fun deleteProfilePicture(profile: UserProfile): UserProfile {
        if (profile.profilePicture != null) {
            fileService.deleteFile(profile.profilePicture!!)
        }
        val newProfilePicture = UserUtils.getProfileAvatar(profile.firstName, profile.lastName)
        userProfileRepository.updateProfilePicture(profile, newProfilePicture)
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
        val notNeeded = listOf(
            user,
            superAdminProvider.superAdmin,
            *followings.toTypedArray()
        )

        val suggestedFollowings =
            userProfileRepository.findByUserNotIn(notNeeded, PageRequest.of(0, MAX_SUGGESTED_FOLLOWINGS))
        suggestedFollowings.forEach {
            it.user.__isFollowed = false
        }
        return suggestedFollowings
    }

    override fun getProfileMeta(username: String): ProfileMetaDto {
        val profile = userProfileRepository.findByUserUsername(username) ?: throw NoEntityWithIdException.create(
            "UserProfile",
            username
        )
        val followerCount = followerServices.countFollowersOf(profile.user)
        val followingCount = followerServices.countFollowingsOf(profile.user)
        val ratingCount = ratingRepository.countAllByUser(profile.user).toInt()
        val reviewCount = ratingRepository.countUserReviews(profile.user).toInt()
        return ProfileMetaDto(
            followerCount = followerCount,
            followingCount = followingCount,
            ratingCount = ratingCount,
            reviewCount = reviewCount
        )
    }

    override fun getProfileByUsername(username: String): UserProfile {
        return userProfileRepository.findByUserUsername(username) ?: throw NoEntityWithIdException.create(
            "UserProfile",
            username
        )
    }

    override fun searchFriends(
        currentUser: User,
        filters: List<List<FilterDto>>,
        pageRequest: PageRequest
    ): Page<UserProfile> {
        val friends = userProfileRepository.findAll(
            userProfileSpecification.columnEqualsOr(filters),
            pageRequest
        )
        friends.forEach {
            it.user.__isFollowed = followerServices.isFollowing(currentUser, it.user)
        }
        return friends
    }

}