package org.pentales.pentalesrest.models.entities.goal

import org.pentales.pentalesrest.global.repo.base.*
import org.pentales.pentalesrest.global.repo.specifications.*
import org.springframework.stereotype.*
import kotlin.reflect.*
import kotlin.reflect.full.*

@Service
class GoalServices(
    private val goalRepository: GoalRepository
) : IGoalServices {

    override fun findByYear(year: Int): Goal? {
        return goalRepository.findGoalByYear(year)
    }

    override val repository: IRepoSpecification<Goal, Long>
        get() = goalRepository
    override val modelProperties: Collection<KProperty1<Goal, *>>
        get() = Goal::class.memberProperties
    override val specification: ISpecification<Goal>
        get() = object : ISpecification<Goal> {}
}