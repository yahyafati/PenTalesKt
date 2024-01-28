package org.pentales.pentalesrest.services.basic

import org.pentales.pentalesrest.models.User
import org.springframework.data.domain.*
import org.springframework.security.core.userdetails.*

interface IUserServices : UserDetailsService {

    fun save(user: User): User
    fun saveNew(user: User): User
    fun findByUsername(username: String): User
    fun findById(id: Long): User
    fun findAll(pageable: Pageable): Page<User>
    fun existsByUsername(username: String): Boolean
    fun deleteById(id: Long)
    fun deleteByUsername(username: String)

    fun getModerators(page: Pageable): Page<User>
    fun toggleModerator(user: User): Boolean
    fun makeModerator(user: User): Boolean
    fun removeModerator(user: User): Boolean
}