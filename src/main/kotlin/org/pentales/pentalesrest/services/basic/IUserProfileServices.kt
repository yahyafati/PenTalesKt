package org.pentales.pentalesrest.services.basic

import org.pentales.pentalesrest.dto.*
import org.pentales.pentalesrest.models.*

interface IUserProfileServices {

    fun update(profile: UpdateProfileDto, updatedFields: List<String>): UserProfile?

    fun save(profile: UserProfile): UserProfile

}