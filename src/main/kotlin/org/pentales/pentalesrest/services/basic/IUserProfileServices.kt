package org.pentales.pentalesrest.services.basic

import org.pentales.pentalesrest.dto.file.*
import org.pentales.pentalesrest.dto.user.*
import org.pentales.pentalesrest.models.*

interface IUserProfileServices {

    fun update(profile: UpdateProfileDto, updatedFields: List<String>): UserProfile

    fun save(profile: UserProfile): UserProfile

    fun uploadProfilePicture(userProfile: UserProfile, uploadDto: ImageUploadDto): UserProfile

}