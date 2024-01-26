package org.pentales.pentalesrest.repo

import org.pentales.pentalesrest.models.enums.*
import org.pentales.pentalesrest.models.view.*
import org.pentales.pentalesrest.repo.base.*

interface ActivityViewRepository : IReadOnlyRepository<ActivityView, Long> {

    fun findByActivityIdAndType(activityId: Long, type: EActivityType): ActivityView?
}