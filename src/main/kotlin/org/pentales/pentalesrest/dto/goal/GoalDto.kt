package org.pentales.pentalesrest.dto.goal

import org.pentales.pentalesrest.models.*

data class GoalDto(
    var year: Int = 0,
    var title: String = "",
    var description: String = "",
) {

    constructor(goal: Goal) : this(
        year = goal.year,
        title = goal.title,
        description = goal.description,
    )
}
