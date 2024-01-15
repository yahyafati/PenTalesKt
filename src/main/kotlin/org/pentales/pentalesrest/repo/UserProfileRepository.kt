package org.pentales.pentalesrest.repo

import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.repo.base.IRepoSpecification

interface UserProfileRepository : IRepoSpecification<UserProfile, Long> {

    fun findByUserUsername(username: String): UserProfile?
}