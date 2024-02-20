package org.pentales.pentalesrest.repo

import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.models.intermediates.*
import org.pentales.pentalesrest.models.keys.*
import org.pentales.pentalesrest.repo.base.*

interface AuthorityUserRepository : IRepoSpecification<AuthorityUser, Long> {

    fun findAllByUser(user: User): List<AuthorityUser>

    fun deleteAllByUser(user: User): Int
    fun deleteAllByIdIn(id: MutableCollection<AuthorityUserKey>): Int
    fun deleteAllByUserAndIdNotIn(user: User, id: MutableCollection<AuthorityUserKey>): Int
}