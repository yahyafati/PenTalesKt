package org.pentales.pentalesrest.dto.user

import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.models.enums.*

data class UserSecurityDto(
    var id: Long = 0L,
    var firstName: String? = "",
    var lastName: String? = "",
    var username: String = "",
    var email: String = "",
    var role: ERole = ERole.ROLE_USER,
    var permissions: Collection<EPermission> = listOf(),
    var profilePicture: String? = "",
) {

    constructor(user: User, baseURL: String) : this(
        id = user.id,
        firstName = user.profile?.firstName,
        lastName = user.profile?.lastName,
        username = user.username,
        role = user.role.role,
        permissions = user.authorities.map { it.permission },
        email = user.email,
        profilePicture = UserDto.getProfilePictureWithBaseURL(user.profile, baseURL)
    )
}
