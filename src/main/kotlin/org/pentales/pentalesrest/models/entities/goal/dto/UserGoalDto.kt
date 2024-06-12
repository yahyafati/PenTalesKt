package org.pentales.pentalesrest.models.entities.goal.dto

import org.pentales.pentalesrest.models.entities.goal.userGoal.*
import java.time.*

data class UserGoalDto(
    var target: Int = 0,
    var year: Int = Year.now().value,
    val goalId: Long? = 0,
    val current: Int = 0,
) {

    constructor(userGoal: UserGoal) : this(
        target = userGoal.target,
        year = userGoal.goal.year,
        goalId = userGoal.goal.id,
        current = userGoal.__current,
    )
}