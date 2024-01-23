package org.pentales.pentalesrest.services.basic

import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.models.intermediates.*

interface IUserGoalServices {

    fun findByUserAndGoal(user: User, goal: Goal): UserGoal?

    fun findByUserAndGoalYear(user: User, year: Int): UserGoal?

    fun save(userGoal: UserGoal): UserGoal

    fun setYearsGoal(userId: Long, target: Int, year: Int): UserGoal

    fun getUserGoalStatusByYear(userId: Long, year: Int): UserGoal?

    fun getHistory(userId: Long, lastN: Int): List<UserGoal>

}