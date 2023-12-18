package org.pentales.pentalesrest.services.basic.impl

import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.models.intermediates.*
import org.pentales.pentalesrest.models.keys.*
import org.pentales.pentalesrest.repo.*
import org.pentales.pentalesrest.services.basic.*
import org.springframework.stereotype.*

@Service
class UserGoalServices(
    private val userGoalRepository: UserGoalRepository,
    private val goalRepository: GoalRepository,
) : IUserGoalServices {

    override fun findByUserAndGoal(user: User, goal: Goal): UserGoal? {
        return userGoalRepository.findByUserAndGoal(user, goal)
    }

    override fun findByUserAndGoalYear(user: User, year: Int): UserGoal? {
        return userGoalRepository.findByUserAndGoalYear(user, year)
    }

    override fun save(userGoal: UserGoal): UserGoal {
        return userGoalRepository.save(userGoal)
    }

    override fun setYearsGoal(userId: Long, target: Int, year: Int): UserGoal {
        val goal = goalRepository.findGoalByYear(year)
        val key = UserGoalKey(userId = userId, goalId = goal.id)
        val userGoal = UserGoal(id = key, target = target, user = User(userId), goal = goal)
        return save(userGoal)
    }
}