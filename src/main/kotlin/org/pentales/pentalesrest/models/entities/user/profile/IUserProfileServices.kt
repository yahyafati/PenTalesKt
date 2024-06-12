package org.pentales.pentalesrest.models.entities.user.profile

import org.pentales.pentalesrest.global.dto.*
import org.pentales.pentalesrest.global.dto.file.*
import org.pentales.pentalesrest.models.entities.user.*
import org.pentales.pentalesrest.models.entities.user.dto.*
import org.springframework.data.domain.*

interface IUserProfileServices {

    fun update(profile: UpdateProfileDto, updatedFields: List<String>): UserProfile

    fun save(profile: UserProfile): UserProfile

    fun uploadProfilePicture(userProfile: UserProfile, uploadDto: ImageUploadDto): UserProfile

    fun uploadProfileCover(userProfile: UserProfile, uploadDto: ImageUploadDto): UserProfile

    fun getFollowers(user: User, pageable: Pageable): Page<User>
    fun getFollowers(username: String, pageable: Pageable): Page<User>

    fun getFollowings(user: User, pageable: Pageable): Page<User>
    fun getFollowings(username: String, pageable: Pageable): Page<User>
    fun deleteProfilePicture(profile: UserProfile): UserProfile
    fun deleteProfileCover(profile: UserProfile): UserProfile
    fun getSuggestedFollowings(user: User): List<UserProfile>
    fun getProfileMeta(username: String): ProfileMetaDto
    fun getProfileByUsername(username: String): UserProfile
    fun searchFriends(currentUser: User, filters: List<List<FilterDto>>, pageRequest: PageRequest): Page<UserProfile>

    fun uploadProfilePicture(
        userProfile: UserProfile,
        byteArray: ByteArray,
        originalFilename: String = "profile.jpg"
    ): UserProfile
}