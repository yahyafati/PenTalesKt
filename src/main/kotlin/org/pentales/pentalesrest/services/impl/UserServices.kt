package org.pentales.pentalesrest.services.impl

import org.pentales.pentalesrest.models.User
import org.pentales.pentalesrest.repo.*
import org.pentales.pentalesrest.services.*
import org.springframework.security.core.userdetails.*
import org.springframework.stereotype.*

@Service
class UserServices(private val userRepository: UserRepository) : IUserService {

    override fun findByUsername(username: String): User {
        return userRepository.findByUsername(username) ?: throw UsernameNotFoundException("User not found")
    }

    override fun loadUserByUsername(username: String?): UserDetails {
        if (username == null) {
            throw UsernameNotFoundException("Username is null")
        }
        return findByUsername(username)
    }
}