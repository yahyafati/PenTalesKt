package org.pentales.pentalesrest.models.entities.activity

import org.pentales.pentalesrest.models.entities.user.*
import org.springframework.data.domain.*

interface IActivityViewServices {

    fun getActivities(currentUser: User, pageable: Pageable): Page<ActivityView>
    fun getActivitiesBy(currentUser: User, username: String, pageable: Pageable): Page<ActivityView>
    fun getActivity(user: User, ratingId: Long, activityId: Long?, type: EActivityType): ActivityView
    fun getExploreActivities(user: User, pageable: Pageable): Page<ActivityView>
}