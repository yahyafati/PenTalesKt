package org.pentales.pentalesrest.models.entities.entityKeys

import jakarta.persistence.*
import java.io.*

@Embeddable
class UserRatingKey(
    val userId: Long = 0L,
    val ratingId: Long = 0L,
) : Serializable {

    override fun toString(): String {
        return "UserRatingKey(userId=$userId, ratingId=$ratingId)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UserRatingKey

        if (userId != other.userId) return false
        if (ratingId != other.ratingId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = userId.hashCode()
        result = 31 * result + ratingId.hashCode()
        return result
    }

}