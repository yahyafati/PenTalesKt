package org.pentales.pentalesrest.services.basic

import org.pentales.pentalesrest.models.*
import org.springframework.data.domain.*

interface IActivityServices {

    fun getActivities(pageable: Pageable): Page<Activity>
    fun getActivityById(id: Long): Activity
}