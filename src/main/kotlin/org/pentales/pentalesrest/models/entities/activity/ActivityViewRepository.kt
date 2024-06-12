package org.pentales.pentalesrest.models.entities.activity

import org.pentales.pentalesrest.global.repo.base.*
import org.pentales.pentalesrest.models.entities.user.*
import org.springframework.data.domain.*
import java.util.*

interface ActivityViewRepository : IReadOnlyRepository<ActivityView, UUID> {

    fun findByActivityIdAndType(activityId: Long, type: EActivityType): ActivityView?
    fun findAllByUser(user: User, pageable: Pageable): Page<ActivityView>
    fun findAllByUserUsername(username: String, pageable: Pageable): Page<ActivityView>
    fun findAllByUserIn(users: List<User>, pageable: Pageable): Page<ActivityView>
}