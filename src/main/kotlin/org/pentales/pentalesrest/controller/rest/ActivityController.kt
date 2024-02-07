package org.pentales.pentalesrest.controller.rest

import org.pentales.pentalesrest.dto.*
import org.pentales.pentalesrest.dto.activity.*
import org.pentales.pentalesrest.models.enums.*
import org.pentales.pentalesrest.security.*
import org.pentales.pentalesrest.services.basic.*
import org.pentales.pentalesrest.utils.*
import org.springframework.data.domain.*
import org.springframework.http.*
import org.springframework.web.bind.annotation.*
import org.springframework.web.context.request.*

@RestController
@RequestMapping("/api/activity")
class ActivityController(
    private val activityViewServices: IActivityViewServices,
    private val authenticationFacade: IAuthenticationFacade,
) {

    @GetMapping
    fun getActivities(
        @RequestParam
        page: Int?,
        @RequestParam
        size: Int?
    ): ResponseEntity<BasicResponseDto<Page<ActivityDto>>> {
        val currentUser = authenticationFacade.forcedCurrentUser
        val pageRequest = IBasicControllerSkeleton.getPageRequest(page, size, "createdAt", Sort.Direction.DESC)
        val activities = activityViewServices.getActivities(currentUser, pageRequest)
        val request = (RequestContextHolder.getRequestAttributes() as ServletRequestAttributes?)!!.request
        return ResponseEntity.ok(
            BasicResponseDto.ok(activities.map { ActivityDto(it, ServletUtil.getBaseURL(request)) })
        )
    }

    @GetMapping("/{id}")
    fun getActivity(
        @PathVariable
        id: Long,
        @RequestParam(required = false)
        activityId: Long?,
        @RequestParam(defaultValue = "RATING", required = false)
        type: String?
    ): ResponseEntity<BasicResponseDto<ActivityDto>> {
        val currentUser = authenticationFacade.forcedCurrentUser
        val eType = if (type == null) EActivityType.RATING else EActivityType.fromString(type, EActivityType.RATING)
        val activity = activityViewServices.getActivity(currentUser, id, activityId, eType)
        val request = (RequestContextHolder.getRequestAttributes() as ServletRequestAttributes?)!!.request
        return ResponseEntity.ok(
            BasicResponseDto.ok(ActivityDto(activity, ServletUtil.getBaseURL(request)))
        )
    }

}