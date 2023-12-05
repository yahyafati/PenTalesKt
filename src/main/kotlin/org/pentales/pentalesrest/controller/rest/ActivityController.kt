package org.pentales.pentalesrest.controller.rest

import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.services.basic.*
import org.springframework.data.domain.*
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/activity")
class ActivityController(
    private val activityServices: IActivityServices
) {

    @GetMapping
    fun getActivities(
        @RequestParam
        page: Int?,
        @RequestParam
        size: Int?
    ): Page<Activity> {
        val pageRequest = IBasicControllerSkeleton.getPageRequest(page, size, "createdAt", Sort.Direction.DESC)
        return activityServices.getActivities(pageRequest)
    }

    @GetMapping("/{id}")
    fun getActivityById(
        @PathVariable
        id: Long
    ): Activity {
        return activityServices.getActivityById(id)
    }

}