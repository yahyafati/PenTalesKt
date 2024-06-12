package org.pentales.pentalesrest.models.entities.user

import org.pentales.pentalesrest.global.repo.base.*
import org.pentales.pentalesrest.models.entities.user.authority.*
import org.pentales.pentalesrest.models.entities.user.role.*
import org.springframework.data.domain.*
import org.springframework.data.jpa.repository.*

interface UserRepository : IRepoSpecification<User, Long> {

    fun findByUsername(username: String): User?
    fun existsByUsernameIgnoreCase(username: String): Boolean
    fun findByEmail(email: String): User?
    fun existsByEmailIgnoreCase(email: String): Boolean
    fun deleteByUsername(username: String)
    fun findAllByRole(role: Role, pageable: Pageable): Page<User>
    fun findAllByRoleIn(roles: Set<Role>, pageable: Pageable): Page<User>

    fun findAllByAuthoritiesAuthority(authority: Authority, pageable: Pageable): Page<User>
    fun findAllByAuthoritiesAuthorityIn(authorities: Set<Authority>, pageable: Pageable): Page<User>

    @Query("Update User u set u.password = :password where u.id = :id")
    @Modifying
    fun changePassword(id: Long, password: String): Int

    @Query("Update User u set u.isEnabled = false where u.id = :id")
    @Modifying
    fun disable(id: Long): Int

    @Query("DELETE FROM Authority a WHERE a.id = :id")
    fun deleteAllAuthorities(id: Long): Int

}