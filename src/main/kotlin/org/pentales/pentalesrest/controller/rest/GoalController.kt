package org.pentales.pentalesrest.controller.rest

import org.pentales.pentalesrest.dto.*
import org.pentales.pentalesrest.dto.goal.*
import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.models.enums.*
import org.pentales.pentalesrest.security.*
import org.pentales.pentalesrest.services.basic.*
import org.pentales.pentalesrest.utils.*
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
}