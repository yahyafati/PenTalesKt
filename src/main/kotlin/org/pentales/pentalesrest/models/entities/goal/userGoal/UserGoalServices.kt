package org.pentales.pentalesrest.models.entities.goal.userGoal

import org.pentales.pentalesrest.config.properties.*
import org.pentales.pentalesrest.models.entities.book.userBook.activity.*
import org.pentales.pentalesrest.models.entities.book.userBook.status.*
import org.pentales.pentalesrest.models.entities.entityKeys.*
import org.pentales.pentalesrest.models.entities.goal.*
import org.pentales.pentalesrest.models.entities.user.*
import org.pentales.pentalesrest.utils.*
import org.springframework.data.domain.*
import org.springframework.stereotype.*

@Service
class UserGoalServices(
    private val userGoalRepository: UserGoalRepository,
    private val goalRepository: GoalRepository,
    private val defaultGoalConfig: GoalProperties.DefaultGoalProperties,
    private val userBookActivityServices: IUserBookActivityServices,
) : IUserGoalServices {

    fun getReadCountByYear(userId: Long, year: Int): Int {
        return userBookActivityServices.getBooksCountByStatusInYear(
            userId = userId,
            status = EUserBookReadStatus.READ,
            year = year,
        )
    }

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
        val goal: Goal = goalRepository.findGoalByYear(year) ?: run {
            val newGoal = Goal(
                title = defaultGoalConfig.title,
                description = defaultGoalConfig.description,
                year = year,
            )
            goalRepository.save(newGoal)
        }

        val key = UserGoalKey(userId = userId, goalId = goal.id)
        val userGoal = UserGoal(id = key, target = target, user = User(userId), goal = goal)
        return save(userGoal)
    }

    override fun getUserGoalStatusByYear(userId: Long, year: Int): UserGoal? {
        val userGoal = findByUserAndGoalYear(User(userId), year)
        if (userGoal != null) {
            val readBooksCount = getReadCountByYear(userId, year)
            userGoal.__current = readBooksCount
        }
        return userGoal
    }

    override fun getHistory(userId: Long, lastN: Int): List<UserGoal> {
        val pageable = PageableUtil.getPageable(
            0, lastN, sort = Sort.by("goal.year").descending()
        )
        val userGoals = userGoalRepository.findByUser(User(userId), pageable)
        userGoals.forEach {
            val readBooksCount = getReadCountByYear(userId, it.goal.year)
            it.__current = readBooksCount
        }
        return userGoals
    }
}