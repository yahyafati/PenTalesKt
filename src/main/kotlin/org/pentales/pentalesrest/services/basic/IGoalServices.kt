package org.pentales.pentalesrest.services.basic

import org.pentales.pentalesrest.models.*

interface IGoalServices : IGenericService<Goal> {

    fun findByYear(year: Int): Goal?

}