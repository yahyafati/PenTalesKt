package org.pentales.pentalesrest.repo

import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.repo.base.*
import org.springframework.data.jpa.repository.*

interface UserProfileRepository : IRepoSpecification<UserProfile, Long> {

    fun findByUserUsername(username: String): UserProfile?

    @Query("UPDATE UserProfile u SET u.profilePicture = :profilePicture WHERE u = :userProfile")
    @Modifying
    fun updateProfilePicture(userProfile: UserProfile, profilePicture: String): Int

    @Query("UPDATE UserProfile u SET u.coverPicture = :coverPicture WHERE u = :userProfile")
    @Modifying
    fun updateCoverPicture(userProfile: UserProfile, coverPicture: String): Int

}