package org.pentales.pentalesrest.services.basic.impl

import org.pentales.pentalesrest.dto.user.*
import org.pentales.pentalesrest.exceptions.*
import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.models.User
import org.pentales.pentalesrest.models.enums.*
import org.pentales.pentalesrest.models.intermediates.*
import org.pentales.pentalesrest.models.keys.*
import org.pentales.pentalesrest.repo.*
import org.pentales.pentalesrest.services.basic.*
import org.springframework.data.domain.*
import org.springframework.http.*
import org.springframework.security.core.userdetails.*
import org.springframework.security.crypto.password.*
import org.springframework.stereotype.*
import org.springframework.transaction.annotation.*

@Service
class UserServices(
    private val userRepository: UserRepository,
    private val roleRepository: RoleRepository,
    private val passwordEncoder: PasswordEncoder,
    private val authorityUserRepository: AuthorityUserRepository,
) : IUserServices {

    @Transactional
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
        return userRepository.findAllByAuthoritiesAuthority(Authority(permission = EPermission.MODERATOR_ACCESS), page)
    }

    override fun findAllByRole(role: ERole, pageable: Pageable): Page<User> {
        val roleEntity = Role(role = role)
        return userRepository.findAllByRole(roleEntity, pageable)
    }

    override fun findAllByRoles(roles: Set<ERole>, pageable: Pageable): Page<User> {
        val roleEntities = roles.map { Role(role = it) }.toSet()
        return userRepository.findAllByRoleIn(roleEntities, pageable)
    }

    override fun findAllByPermissions(permissions: Set<EPermission>, pageable: Pageable): Page<User> {
        val authorities = permissions.map { Authority(permission = it) }.toSet()
        return userRepository.findAllByAuthoritiesAuthorityIn(authorities, pageable)
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

    override fun addPermissions(user: User, permissions: Set<EPermission>): User {
        val addedAuthorities = permissions.map {
            AuthorityUser(
                id = AuthorityUserKey(userId = user.id, authorityId = it.id),
                user = user,
                authority = Authority(permission = it)
            )
        }
        authorityUserRepository.saveAll(addedAuthorities)
        return user
    }

    @Transactional
    override fun removePermissions(user: User, permissions: Set<EPermission>): User {
        val removedAuthorities = permissions.toSet().map {
            AuthorityUser(
                id = AuthorityUserKey(userId = user.id, authorityId = it.id),
                user = user,
                authority = Authority(permission = it)
            )
        }.toSet()
        authorityUserRepository.deleteAll(removedAuthorities)
        user.authorities.removeAll(removedAuthorities)
        return user
    }

    @Transactional
    fun setPermissions(user: User, permissions: Set<EPermission>): User {
        user.authorities.clear()
        authorityUserRepository.deleteAllByUser(user)
        return addPermissions(user, permissions)
    }

    @Transactional
    override fun setPermissions(username: String, permissions: Set<EPermission>): User {
        val user = findByUsername(username)
        setPermissions(user, permissions)
        user.authorities = authorityUserRepository.findAllByUser(user).toMutableSet()
        return user
    }

    @Transactional
    override fun changePassword(user: User, changePassword: ChangePasswordDto) {
        if (!passwordEncoder.matches(changePassword.oldPassword, user.password)) {
            throw GenericException("Old password is incorrect", status = HttpStatus.BAD_REQUEST)
        }
        user.password = passwordEncoder.encode(changePassword.newPassword)
        userRepository.changePassword(user.id, user.password)
    }

    @Transactional
    override fun disable(user: User) {
        userRepository.disable(user.id)
    }

    override fun findByEmail(email: String): User? {
        return userRepository.findByEmail(email)
    }

    override fun existsByEmail(email: String): Boolean {
        return userRepository.existsByEmail(email)
    }

    override fun loadUserByUsername(username: String?): UserDetails {
        if (username == null) {
            throw UsernameNotFoundException("Username is null")
        }
        return findByUsername(username)
    }
}