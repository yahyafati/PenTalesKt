package org.pentales.pentalesrest.services

import org.pentales.pentalesrest.models.User
import org.springframework.security.core.userdetails.*

interface IUserService : UserDetailsService {

    fun findByUsername(username: String): User
}