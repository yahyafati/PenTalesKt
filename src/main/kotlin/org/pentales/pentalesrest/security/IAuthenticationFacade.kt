package org.pentales.pentalesrest.security

import org.pentales.pentalesrest.models.User
import org.springframework.security.core.*
import org.springframework.security.core.userdetails.*

interface IAuthenticationFacade {

    val authentication: Authentication?

    companion object {

        fun equals(username1: String?, username2: String?): Boolean {
            if (username1 == null || username2 == null) return false
            return username1.equals(username2, ignoreCase = true)
        }

    }

    val username: String?
        get() {
            val principal = authentication?.principal
            if (principal is User) {
                return principal.username
            }
            return null
        }

    val currentUser: User?
        get() {
            val principal = authentication?.principal
            if (principal is User) {
                return principal
            }
            return null
        }

    val forcedCurrentUser: User
        get() {
            val principal = authentication?.principal
            if (principal is User) {
                return principal
            }
            throw UsernameNotFoundException("No user logged in")
        }

    val currentUserId: Long
        get() = forcedCurrentUser.id
}