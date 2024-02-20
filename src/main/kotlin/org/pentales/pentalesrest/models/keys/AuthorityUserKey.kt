package org.pentales.pentalesrest.models.keys

import jakarta.persistence.*
import java.io.*

@Embeddable
class AuthorityUserKey(
    var userId: Long = 0L,
    var authorityId: Long = 0L,
) : Serializable {

    override fun toString(): String {
        return "AuthorityUserKey(userId=$userId, authorityId=$authorityId)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AuthorityUserKey

        if (userId != other.userId) return false
        if (authorityId != other.authorityId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = userId.hashCode()
        result = 31 * result + authorityId.hashCode()
        return result
    }
}