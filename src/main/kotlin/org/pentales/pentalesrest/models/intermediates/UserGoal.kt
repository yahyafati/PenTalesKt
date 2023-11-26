package org.pentales.pentalesrest.models.intermediates

import jakarta.persistence.*
import jakarta.validation.constraints.*
import org.pentales.pentalesrest.models.*
import java.io.*

@Entity
class UserGoal(
    @EmbeddedId
    var id: UserGoalKey = UserGoalKey(),
    @field:Min(1)
    var target: Int = 1

) : IAudit() {}

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