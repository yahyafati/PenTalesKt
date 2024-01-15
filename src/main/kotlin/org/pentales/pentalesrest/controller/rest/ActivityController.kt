package org.pentales.pentalesrest.controller.rest

import org.pentales.pentalesrest.dto.*
import org.pentales.pentalesrest.services.basic.*
import org.springframework.data.domain.*
import org.springframework.http.*
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/activity")
class ActivityController(
    private val activityViewServices: IActivityViewServices
) {

    @GetMapping
    fun getActivities(
        @RequestParam
        page: Int?,
        @RequestParam
        size: Int?
    ): ResponseEntity<BasicResponseDto<Page<ActivityDto>>> {
        val pageRequest = IBasicControllerSkeleton.getPageRequest(page, size, "createdAt", Sort.Direction.DESC)
        val activities = activityViewServices.getActivities(pageRequest)
        return ResponseEntity.ok(
            BasicResponseDto.ok(activities.map { ActivityDto(it) })
        )
    }

}