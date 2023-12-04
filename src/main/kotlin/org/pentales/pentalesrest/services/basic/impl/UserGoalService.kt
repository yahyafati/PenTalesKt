package org.pentales.pentalesrest.services.basic.impl

import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.models.intermediates.*
import org.pentales.pentalesrest.repo.*
import org.pentales.pentalesrest.services.basic.*
import org.springframework.stereotype.*

@Service
class UserGoalService(
    private val userGoalRepository: UserGoalRepository
) : IUserGoalService {

    override fun findByUserAndGoal(user: User, goal: Goal): UserGoal? {
        return userGoalRepository.findByUserAndGoal(user, goal)
    }

    override fun findByUserAndGoalYear(user: User, year: Int): UserGoal? {
        return userGoalRepository.findByUserAndGoalYear(user, year)
    }

    override fun save(userGoal: UserGoal): UserGoal {
        return userGoalRepository.save(userGoal)
    }
}