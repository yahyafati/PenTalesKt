package org.pentales.pentalesrest.repo

import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.repo.base.IRepoSpecification

interface GoalRepository : IRepoSpecification<Goal, Long> {

    fun findGoalByYear(year: Int): Goal
}