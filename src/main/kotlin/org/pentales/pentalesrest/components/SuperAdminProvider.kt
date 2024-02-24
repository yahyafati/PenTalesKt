package org.pentales.pentalesrest.components

import org.pentales.pentalesrest.exceptions.*
import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.repo.*
import org.springframework.stereotype.*

@Component
class SuperAdminProvider(
    userRepository: UserRepository,
) {

    final var superAdmin: User
    final var id: Long

    init {
        val superAdmin = userRepository.findByUsername("superadmin")
            ?: throw NoEntityWithIdException("No superadmin found!")
        val clone = User(
            id = superAdmin.id,
            username = superAdmin.username,
            password = superAdmin.password,
            email = superAdmin.email,
            profile = superAdmin.profile,
            provider = superAdmin.provider,
        )

        this.superAdmin = clone
        this.id = clone.id
    }

}