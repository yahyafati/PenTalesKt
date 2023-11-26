package org.pentales.pentalesrest.models.keys

import jakarta.persistence.*
import java.io.*

@Embeddable
class UserUserKey(
    var followerId: Long = 0L, var followingId: Long = 0L
) : Serializable {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UserUserKey

        if (followerId != other.followerId) return false
        if (followingId != other.followingId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = followerId.hashCode()
        result = 31 * result + followingId.hashCode()
        return result
    }

    override fun toString(): String {
        return "UserUserKey(followerId=$followerId, followingId=$followingId)"
    }

}