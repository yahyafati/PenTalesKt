package org.pentales.pentalesrest.models.entities.goal.dto

import org.pentales.pentalesrest.models.entities.goal.*

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
