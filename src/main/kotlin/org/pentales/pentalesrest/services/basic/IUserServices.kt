package org.pentales.pentalesrest.services.basic

import org.pentales.pentalesrest.models.User
import org.springframework.security.core.userdetails.*

interface IUserServices : UserDetailsService {

    fun save(user: User): User
    fun saveNew(user: User): User
    fun findByUsername(username: String): User
    fun existsByUsername(username: String): Boolean
    fun deleteById(id: Long)
    fun deleteByUsername(username: String)
}