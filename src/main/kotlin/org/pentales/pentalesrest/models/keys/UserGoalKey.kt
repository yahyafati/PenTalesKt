package org.pentales.pentalesrest.models.keys

import jakarta.persistence.*
import java.io.*

@Embeddable
class UserGoalKey(
    var userId: Long = 0L, var goalId: Long = 0L
) : Serializable {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UserGoalKey

        if (userId != other.userId) return false
        if (goalId != other.goalId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = userId.hashCode()
        result = 31 * result + goalId.hashCode()
        return result
    }

    override fun toString(): String {
        return "UserGoalKey(userId=$userId, goalId=$goalId)"
    }
}