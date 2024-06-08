package org.pentales.pentalesrest.services

import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.models.enums.*
import org.pentales.pentalesrest.models.view.*
import org.springframework.data.domain.*

interface IActivityViewServices {

    fun getActivities(currentUser: User, pageable: Pageable): Page<ActivityView>
    fun getActivitiesBy(currentUser: User, username: String, pageable: Pageable): Page<ActivityView>
    fun getActivity(user: User, ratingId: Long, activityId: Long?, type: EActivityType): ActivityView
    fun getExploreActivities(user: User, pageable: Pageable): Page<ActivityView>
}