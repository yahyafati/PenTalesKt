package org.pentales.pentalesrest.services.basic

import org.pentales.pentalesrest.dto.user.*
import org.pentales.pentalesrest.models.User
import org.pentales.pentalesrest.models.enums.*
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
    fun findAllByRole(role: ERole, pageable: Pageable): Page<User>
    fun findAllByRoles(roles: Set<ERole>, pageable: Pageable): Page<User>
    fun findAllByPermissions(permissions: Set<EPermission>, pageable: Pageable): Page<User>
    fun toggleModerator(user: User): Boolean
    fun makeModerator(user: User): Boolean
    fun removeModerator(user: User): Boolean
    fun changeRole(user: User, role: ERole): Boolean
    fun addPermissions(user: User, permissions: Set<EPermission>): User
    fun removePermissions(user: User, permissions: Set<EPermission>): User
    fun setPermissions(username: String, permissions: Set<EPermission>): User
    fun changePassword(user: User, changePassword: ChangePasswordDto)
    fun disable(user: User)

}