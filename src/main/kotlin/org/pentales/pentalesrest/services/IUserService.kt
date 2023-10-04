package org.pentales.pentalesrest.services

import org.pentales.pentalesrest.models.User
import org.springframework.security.core.userdetails.*

interface IUserService : UserDetailsService {

    fun save(user: User): User
    fun saveNew(user: User): User
    fun findByUsername(username: String): User
    fun deleteById(id: Long)
    fun deleteByUsername(username: String)
}