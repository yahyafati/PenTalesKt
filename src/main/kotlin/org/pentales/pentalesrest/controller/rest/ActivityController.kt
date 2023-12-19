package org.pentales.pentalesrest.controller.rest

import org.pentales.pentalesrest.dto.*
import org.pentales.pentalesrest.dto.activity.*
import org.pentales.pentalesrest.services.basic.*
import org.springframework.data.domain.*
import org.springframework.http.*
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
    ): ResponseEntity<Page<ActivityDto>> {
        val pageRequest = IBasicControllerSkeleton.getPageRequest(page, size, "createdAt", Sort.Direction.DESC)
        return ResponseEntity.ok(activityServices.getActivities(pageRequest).map { ActivityDto(it) })
    }

    @GetMapping("/{id}")
    fun getActivityById(
        @PathVariable
        id: Long
    ): ResponseEntity<BasicResponseDto<ActivityDto>> {
        val dto = ActivityDto(activityServices.getActivityById(id))
        return ResponseEntity.ok(BasicResponseDto.ok(dto))
    }

}