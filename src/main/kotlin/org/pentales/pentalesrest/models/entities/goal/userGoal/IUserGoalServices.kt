package org.pentales.pentalesrest.models.entities.goal.userGoal

import org.pentales.pentalesrest.models.entities.goal.*
import org.pentales.pentalesrest.models.entities.user.*

interface IUserGoalServices {

    fun findByUserAndGoal(user: User, goal: Goal): UserGoal?

    fun findByUserAndGoalYear(user: User, year: Int): UserGoal?

    fun save(userGoal: UserGoal): UserGoal

    fun setYearsGoal(userId: Long, target: Int, year: Int): UserGoal

    fun getUserGoalStatusByYear(userId: Long, year: Int): UserGoal?

    fun getHistory(userId: Long, lastN: Int): List<UserGoal>

}