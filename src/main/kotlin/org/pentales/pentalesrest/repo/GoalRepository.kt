package org.pentales.pentalesrest.repo

import org.pentales.pentalesrest.models.*

interface GoalRepository : IRepoSpecification<Goal, Long> {

    fun findGoalByYear(year: Int): Goal
}