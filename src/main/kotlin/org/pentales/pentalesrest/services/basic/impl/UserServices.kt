package org.pentales.pentalesrest.services.basic.impl

import org.pentales.pentalesrest.models.User
import org.pentales.pentalesrest.models.enums.*
import org.pentales.pentalesrest.repo.*
import org.pentales.pentalesrest.services.basic.*
import org.springframework.data.domain.*
import org.springframework.security.core.userdetails.*
import org.springframework.stereotype.*

@Service
class UserServices(private val userRepository: UserRepository) : IUserServices {

    override fun save(user: User): User {
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
        return userRepository.findAllByRole(ERole.MODERATOR, page)
    }

    override fun toggleModerator(user: User): Boolean {
        user.role = if (user.role == ERole.MODERATOR) ERole.USER else ERole.MODERATOR
        save(user)
        return user.role == ERole.MODERATOR
    }

    override fun makeModerator(user: User): Boolean {
        user.role = ERole.MODERATOR
        save(user)
        return user.role == ERole.MODERATOR
    }

    override fun removeModerator(user: User): Boolean {
        user.role = ERole.USER
        save(user)
        return user.role == ERole.USER
    }

    override fun loadUserByUsername(username: String?): UserDetails {
        if (username == null) {
            throw UsernameNotFoundException("Username is null")
        }
        return findByUsername(username)
    }
}