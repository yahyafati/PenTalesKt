package org.pentales.pentalesrest.dto.goal

import org.pentales.pentalesrest.models.intermediates.*
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
    )
}