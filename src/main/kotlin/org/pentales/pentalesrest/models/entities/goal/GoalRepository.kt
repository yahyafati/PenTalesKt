package org.pentales.pentalesrest.models.entities.goal

import org.pentales.pentalesrest.global.repo.base.*

interface GoalRepository : IRepoSpecification<Goal, Long> {

    fun findGoalByYear(year: Int): Goal?
}