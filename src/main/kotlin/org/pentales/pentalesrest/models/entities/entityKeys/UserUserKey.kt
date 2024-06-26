package org.pentales.pentalesrest.models.entities.entityKeys

import jakarta.persistence.*
import java.io.*

@Embeddable
class UserUserKey(
    var followerId: Long = 0L, var followedId: Long = 0L
) : Serializable {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UserUserKey

        if (followerId != other.followerId) return false
        if (followedId != other.followedId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = followerId.hashCode()
        result = 31 * result + followedId.hashCode()
        return result
    }

    override fun toString(): String {
        return "UserUserKey(followerId=$followerId, followingId=$followedId)"
    }

}