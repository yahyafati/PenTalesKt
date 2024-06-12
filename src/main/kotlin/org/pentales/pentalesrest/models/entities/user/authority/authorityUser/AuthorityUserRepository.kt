package org.pentales.pentalesrest.models.entities.user.authority.authorityUser

import org.pentales.pentalesrest.global.repo.base.*
import org.pentales.pentalesrest.models.entities.user.*

interface AuthorityUserRepository : IRepoSpecification<AuthorityUser, Long> {

    fun findAllByUser(user: User): List<AuthorityUser>

    fun deleteAllByUser(user: User): Int
}