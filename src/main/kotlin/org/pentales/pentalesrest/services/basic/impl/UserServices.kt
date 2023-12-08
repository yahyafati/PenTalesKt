package org.pentales.pentalesrest.services.basic.impl

import org.pentales.pentalesrest.models.User
import org.pentales.pentalesrest.repo.*
import org.pentales.pentalesrest.services.basic.*
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

    override fun deleteById(id: Long) {
        userRepository.deleteById(id)
    }

    override fun deleteByUsername(username: String) {
        userRepository.deleteByUsername(username)
    }

    override fun loadUserByUsername(username: String?): UserDetails {
        if (username == null) {
            throw UsernameNotFoundException("Username is null")
        }
        return findByUsername(username)
    }
}