package org.pentales.pentalesrest.services.basic

import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.models.view.*
import org.springframework.data.domain.*

interface IActivityViewServices {

    fun getActivities(currentUser: User, pageable: Pageable): Page<ActivityView>

}