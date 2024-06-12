package org.pentales.pentalesrest.models.entities.user.profile

import org.pentales.pentalesrest.global.repo.base.*
import org.pentales.pentalesrest.models.entities.user.*
import org.springframework.data.domain.*
import org.springframework.data.jpa.repository.*

interface UserProfileRepository : IRepoSpecification<UserProfile, Long> {

    fun findByUserUsername(username: String): UserProfile?

    @Query("UPDATE UserProfile u SET u.profilePicture = :profilePicture WHERE u = :userProfile")
    @Modifying
    fun updateProfilePicture(userProfile: UserProfile, profilePicture: String?): Int

    @Query("UPDATE UserProfile u SET u.coverPicture = :coverPicture WHERE u = :userProfile")
    @Modifying
    fun updateCoverPicture(userProfile: UserProfile, coverPicture: String?): Int

    fun findByUserNotIn(users: List<User>, pageable: Pageable): List<UserProfile>

}