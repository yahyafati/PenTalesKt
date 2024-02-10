package org.pentales.pentalesrest.repo

import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.models.enums.*
import org.pentales.pentalesrest.models.view.*
import org.pentales.pentalesrest.repo.base.*
import org.springframework.data.domain.*
import java.util.*

interface ActivityViewRepository : IReadOnlyRepository<ActivityView, UUID> {

    fun findByActivityIdAndType(activityId: Long, type: EActivityType): ActivityView?
    fun findAllByUser(user: User, pageable: Pageable): Page<ActivityView>
    fun findAllByUserUsername(username: String, pageable: Pageable): Page<ActivityView>
    fun findAllByUserIn(users: List<User>, pageable: Pageable): Page<ActivityView>
}