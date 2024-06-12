package org.pentales.pentalesrest.models.entities.goal.userGoal

import org.pentales.pentalesrest.global.repo.base.*
import org.pentales.pentalesrest.models.entities.entityKeys.*
import org.pentales.pentalesrest.models.entities.goal.*
import org.pentales.pentalesrest.models.entities.user.*
import org.springframework.data.domain.*

interface UserGoalRepository : IRepoSpecification<UserGoal, UserGoalKey> {

    fun findByUserAndGoal(user: User, goal: Goal): UserGoal?
    fun findByUserAndGoalYear(user: User, year: Int): UserGoal?
    fun findByUser(user: User, pageable: Pageable): List<UserGoal>

}