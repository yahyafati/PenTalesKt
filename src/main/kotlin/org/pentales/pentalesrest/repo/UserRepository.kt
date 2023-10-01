package org.pentales.pentalesrest.repo

import org.pentales.pentalesrest.models.*
import org.springframework.data.jpa.repository.*

interface UserRepository : JpaRepository<User, Long> {

    fun findByUsername(username: String): User?

    fun existsByUsername(username: String): Boolean

    fun existsByEmail(email: String): Boolean
}