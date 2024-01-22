package org.pentales.pentalesrest.repo

import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.models.intermediates.*
import org.pentales.pentalesrest.models.keys.*
import org.pentales.pentalesrest.repo.base.*
import org.springframework.data.domain.*

interface UserGoalRepository : IRepoSpecification<UserGoal, UserGoalKey> {

    fun findByUserAndGoal(user: User, goal: Goal): UserGoal?
    fun findByUserAndGoalYear(user: User, year: Int): UserGoal?
    fun findByUser(user: User, pageable: Pageable): List<UserGoal>

}