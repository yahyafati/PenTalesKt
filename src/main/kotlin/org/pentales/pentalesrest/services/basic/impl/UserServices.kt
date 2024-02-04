package org.pentales.pentalesrest.services.basic.impl

import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.models.User
import org.pentales.pentalesrest.models.enums.*
import org.pentales.pentalesrest.repo.*
import org.pentales.pentalesrest.services.basic.*
import org.springframework.data.domain.*
import org.springframework.security.core.userdetails.*
import org.springframework.stereotype.*

@Service
class UserServices(
    private val userRepository: UserRepository,
    private val roleRepository: RoleRepository,
) : IUserServices {

    override fun save(user: User): User {
        user.role =
            roleRepository.findById(user.role.id).orElseThrow {
                throw IllegalArgumentException("Role (id=${user.role.id}, role=${user.role.role}) not found")
            }
        return userRepository.save(user)
    }

    override fun saveNew(user: User): User {
        user.id = 0L
        return save(user)
    }

    override fun findByUsername(username: String): User {
        return userRepository.findByUsername(username) ?: throw UsernameNotFoundException("User not found")
    }

    override fun findById(id: Long): User {
        return userRepository.findById(id).orElseThrow { throw UsernameNotFoundException("User not found") }
    }

    override fun findAll(pageable: Pageable): Page<User> {
        return userRepository.findAll(pageable)
    }

    override fun existsByUsername(username: String): Boolean {
        return userRepository.existsByUsername(username)
    }

    override fun deleteById(id: Long) {
        userRepository.deleteById(id)
    }

    override fun deleteByUsername(username: String) {
        userRepository.deleteByUsername(username)
    }

    override fun getModerators(page: Pageable): Page<User> {
        return userRepository.findAllByAuthoritiesContaining(Authority(permission = EPermission.MODERATOR_ACCESS), page)
    }

    override fun findAllByRole(role: ERole, pageable: Pageable): Page<User> {
        val roleEntity = Role(role = role)
        return userRepository.findAllByRole(roleEntity, pageable)
    }

    override fun toggleModerator(user: User): Boolean {
        user.role =
            if (user.role.role == ERole.ROLE_MODERATOR) Role(role = ERole.ROLE_USER)
            else Role(role = ERole.ROLE_MODERATOR)
        save(user)
        return user.role.role == ERole.ROLE_MODERATOR
    }

    override fun makeModerator(user: User): Boolean {
        return changeRole(user, ERole.ROLE_MODERATOR)
    }

    override fun removeModerator(user: User): Boolean {
        return changeRole(user, ERole.ROLE_USER)
    }

    override fun changeRole(user: User, role: ERole): Boolean {
        user.role = Role(role = role)
        save(user)
        return user.role.role == role
    }

    override fun addPermissions(user: User, permissions: Set<EPermission>): Boolean {
        val addedAuthorities = permissions.map { Authority(permission = it) }.toSet()
        user.authorities.addAll(addedAuthorities)
        save(user)
        return user.authorities.containsAll(addedAuthorities)
    }

    override fun removePermissions(user: User, permissions: Set<EPermission>): Boolean {
        val removedAuthorities = permissions.map { Authority(permission = it) }.toSet()
        user.authorities.removeAll(removedAuthorities)
        save(user)
        return !user.authorities.containsAll(removedAuthorities)
    }

    override fun loadUserByUsername(username: String?): UserDetails {
        if (username == null) {
            throw UsernameNotFoundException("Username is null")
        }
        return findByUsername(username)
    }
}