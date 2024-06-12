package org.pentales.pentalesrest.models.entities.user.dto

import org.pentales.pentalesrest.models.entities.user.*
import org.pentales.pentalesrest.models.entities.user.role.*

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
        permissions = user.authorities.map { it.authority.permission },
        email = user.email,
        profilePicture = UserDto.getURLWithBaseURL(user.profile?.profilePicture, baseURL)
    )
}
