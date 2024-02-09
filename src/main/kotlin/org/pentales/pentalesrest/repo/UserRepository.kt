package org.pentales.pentalesrest.repo

import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.repo.base.*
import org.springframework.data.domain.*
import org.springframework.data.jpa.repository.*

interface UserRepository : IRepoSpecification<User, Long> {

    fun findByUsername(username: String): User?
    fun existsByUsername(username: String): Boolean
    fun existsByEmail(email: String): Boolean
    fun deleteByUsername(username: String)
    fun findAllByRole(role: Role, pageable: Pageable): Page<User>

    fun findAllByAuthoritiesContaining(authority: Authority, pageable: Pageable): Page<User>

    @Query("Update User u set u.password = :password where u.id = :id")
    @Modifying
    fun changePassword(id: Long, password: String): Int

    @Query("Update User u set u.isEnabled = false where u.id = :id")
    @Modifying
    fun disable(id: Long): Int

}