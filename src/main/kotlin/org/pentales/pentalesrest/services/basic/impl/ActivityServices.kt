package org.pentales.pentalesrest.services.basic.impl

import org.pentales.pentalesrest.exceptions.*
import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.repo.*
import org.pentales.pentalesrest.services.basic.*
import org.springframework.data.domain.*
import org.springframework.stereotype.*

@Service
class ActivityServices(
    private val activityRepository: ActivityRepository,
) : IActivityServices {

    val entityName = "Activity"

    override fun getActivities(pageable: Pageable): Page<Activity> {
        return activityRepository.findAll(pageable)
    }

    override fun getActivityById(id: Long): Activity {
        return activityRepository.findById(id).orElseThrow {
            NoEntityWithIdException.create(entityName, id)
        }
    }
}