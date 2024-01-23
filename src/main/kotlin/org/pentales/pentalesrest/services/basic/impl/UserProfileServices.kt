package org.pentales.pentalesrest.services.basic.impl

import org.pentales.pentalesrest.dto.user.*
import org.pentales.pentalesrest.exceptions.*
import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.repo.*
import org.pentales.pentalesrest.security.*
import org.pentales.pentalesrest.services.basic.*
import org.springframework.security.core.userdetails.*
import org.springframework.stereotype.*
import kotlin.reflect.*
import kotlin.reflect.full.*

@Service
class UserProfileServices(
    private val userProfileRepository: UserProfileRepository,
    private val bookIntermediatesServices: IBookIntermediatesServices,
    private val authenticationFacade: IAuthenticationFacade,
) : IUserProfileServices {

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
}