package org.pentales.pentalesrest.security

import org.pentales.pentalesrest.models.*
import org.springframework.security.core.*

interface IAuthenticationFacade {

    val authentication: Authentication?

    companion object {

        fun equals(username1: String?, username2: String?): Boolean {
            if (username1 == null || username2 == null) return false
            return username1.equals(username2, ignoreCase = true)
        }

    }

    fun equalsAuth(username1: String): Boolean {
        return equals(username1, username)
    }

    fun equalsAuth(user: User?): Boolean {
        return equals(user?.username, username)
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
            throw Exception("User not found")
        }

    val currentUserId: Long
        get() = forcedCurrentUser.id
}