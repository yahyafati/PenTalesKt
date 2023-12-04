package org.pentales.pentalesrest.services.basic

import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.models.intermediates.*

interface IUserGoalService {

    fun findByUserAndGoal(user: User, goal: Goal): UserGoal?

    fun findByUserAndGoalYear(user: User, year: Int): UserGoal?

    fun save(userGoal: UserGoal): UserGoal

}