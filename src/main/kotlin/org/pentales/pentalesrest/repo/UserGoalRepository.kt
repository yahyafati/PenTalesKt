package org.pentales.pentalesrest.repo

import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.models.intermediates.*
import org.pentales.pentalesrest.models.keys.*

interface UserGoalRepository : IRepoSpecification<UserGoal, UserGoalKey> {

    fun findByUserAndGoal(user: User, goal: Goal): UserGoal?
    fun findByUserAndGoalYear(user: User, year: Int): UserGoal?

}