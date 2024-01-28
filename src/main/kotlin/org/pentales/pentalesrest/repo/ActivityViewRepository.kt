package org.pentales.pentalesrest.repo

import org.pentales.pentalesrest.models.enums.*
import org.pentales.pentalesrest.models.view.*
import org.pentales.pentalesrest.repo.base.*
import java.util.*

interface ActivityViewRepository : IReadOnlyRepository<ActivityView, UUID> {

    fun findByActivityIdAndType(activityId: Long, type: EActivityType): ActivityView?
}