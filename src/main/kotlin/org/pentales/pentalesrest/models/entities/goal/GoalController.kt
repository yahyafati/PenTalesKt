package org.pentales.pentalesrest.models.entities.goal

import org.pentales.pentalesrest.config.security.*
import org.pentales.pentalesrest.global.controller.*
import org.pentales.pentalesrest.global.dto.*
import org.pentales.pentalesrest.models.entities.book.dto.*
import org.pentales.pentalesrest.models.entities.book.userBook.activity.*
import org.pentales.pentalesrest.models.entities.book.userBook.status.*
import org.pentales.pentalesrest.models.entities.goal.dto.*
import org.pentales.pentalesrest.models.entities.goal.userGoal.*
import org.pentales.pentalesrest.models.entities.user.*
import org.pentales.pentalesrest.utils.*
import org.springframework.data.domain.*
import org.springframework.http.*
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/goal")
class GoalController(
    override val service: IGoalServices,
    override val authenticationFacade: IAuthenticationFacade,
    private val userBookActivityServices: IUserBookActivityServices,
    private val userGoalServices: IUserGoalServices,
) : IBasicControllerSkeleton<Goal, IGoalServices> {

    companion object {

        private const val DEFAULT_N = 5
    }

    @GetMapping("/year")
    fun getGoalByYear(
        @RequestParam(defaultValue = "", required = false)
        year: Int?,
    ): ResponseEntity<BasicResponseDto<GoalDto?>> {
        val currentYear = TimeUtil.getCurrentYearUTC()
        val response = service.findByYear(year ?: currentYear)
        return ResponseEntity.ok(BasicResponseDto.ok(response?.let { GoalDto(it) }))
    }

    @GetMapping("/target")
    fun getGoalTarget(
        @RequestParam(required = false)
        year: Int?,
    ): ResponseEntity<BasicResponseDto<UserGoalDto?>> {
        val yearToLoad = year ?: TimeUtil.getCurrentYearUTC()
        val userId = authenticationFacade.currentUserId
        val userGoal = userGoalServices.findByUserAndGoalYear(User(id = userId), yearToLoad)
        val readSoFar = userBookActivityServices.getBooksCountByStatusInYear(
            userId, EUserBookReadStatus.READ, yearToLoad
        )
        return ResponseEntity.ok(BasicResponseDto.ok(userGoal?.let {
            UserGoalDto(
                target = it.target,
                year = it.goal.year,
                current = readSoFar,
                goalId = it.goal.id,
            )
        }))
    }

    @PatchMapping("/target")
    fun updateGoalTarget(
        @RequestBody
        userGoalDto: UserGoalDto,
    ): ResponseEntity<BasicResponseDto<UserGoalDto>> {
        val userId = authenticationFacade.currentUserId
        val userGoal = userGoalServices.setYearsGoal(
            userId = userId, target = userGoalDto.target, year = userGoalDto.year
        )
        return ResponseEntity.ok(BasicResponseDto.ok(UserGoalDto(userGoal)))
    }

    @GetMapping("/books-read")
    fun getBooksRead(
        @RequestParam(required = false)
        year: Int?,
    ): ResponseEntity<BasicResponseDto<Page<ActivityBookDto>>> {
        val userId = authenticationFacade.currentUserId
        val yearToLoad = year ?: TimeUtil.getCurrentYearUTC()
        val userBookActivities = userBookActivityServices.getBooksByStatusInYear(
            userId, EUserBookReadStatus.READ, yearToLoad, PageRequest.of(0, 50)
        )
        val baseURL = ServletUtil.getBaseURLFromCurrentRequest()
        val response = userBookActivities.map { ActivityBookDto(it.book, baseURL) }
        return ResponseEntity.ok(BasicResponseDto.ok(response))
    }

    @GetMapping("/history")
    fun getYears(
        @RequestParam(required = false)
        lastN: Int?,
    ): ResponseEntity<BasicResponseDto<List<UserGoalDto>>> {
        val userId = authenticationFacade.currentUserId
        val userGoals = userGoalServices.getHistory(userId, lastN ?: DEFAULT_N)
        val response = userGoals.map { UserGoalDto(it) }
        return ResponseEntity.ok(BasicResponseDto.ok(response))
    }
}