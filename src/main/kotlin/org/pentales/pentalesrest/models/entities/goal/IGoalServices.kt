package org.pentales.pentalesrest.models.entities.goal

import org.pentales.pentalesrest.global.services.*

interface IGoalServices : IGenericService<Goal> {

    fun findByYear(year: Int): Goal?

}