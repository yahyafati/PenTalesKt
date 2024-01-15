package org.pentales.pentalesrest.repo

import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.repo.base.IRepoSpecification

interface UserRepository : IRepoSpecification<User, Long> {

    fun findByUsername(username: String): User?
    fun existsByUsername(username: String): Boolean
    fun existsByEmail(email: String): Boolean
    fun deleteByUsername(username: String)

}