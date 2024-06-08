package org.pentales.pentalesrest.controller

import org.pentales.pentalesrest.config.security.*
import org.pentales.pentalesrest.dto.*
import org.pentales.pentalesrest.dto.activity.*
import org.pentales.pentalesrest.models.enums.*
import org.pentales.pentalesrest.utils.*
import org.springframework.data.domain.*
import org.springframework.http.*
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/activity")
class ActivityController(
    private val activityViewServices: org.pentales.pentalesrest.services.IActivityViewServices,
    private val authenticationFacade: IAuthenticationFacade,
) {

    @GetMapping
    fun getActivities(
        @RequestParam(required = false)
        explore: Boolean?,
    ): ResponseEntity<BasicResponseDto<Page<ActivityDto>>> {
        val currentUser = authenticationFacade.forcedCurrentUser
        val pageParams = ServletUtil.getPageParamsFromCurrentRequest()
        pageParams.sort = Sort.by(Sort.Direction.DESC, "updatedAt")
        val pageRequest = ServletUtil.getPageRequest(pageParams)
        val activities = if (explore == true) {
            activityViewServices.getExploreActivities(currentUser, pageRequest)
        } else {
            activityViewServices.getActivities(currentUser, pageRequest)
        }
        return ResponseEntity.ok(
            BasicResponseDto.ok(activities.map { ActivityDto(it, ServletUtil.getBaseURLFromCurrentRequest()) })
        )
    }

    @GetMapping("/by")
    fun getActivitiesBy(
        @RequestParam
        username: String,
    ): ResponseEntity<BasicResponseDto<Page<ActivityDto>>> {
        val pageParams = ServletUtil.getPageParamsFromCurrentRequest()
        pageParams.sort = Sort.by(Sort.Direction.DESC, "updatedAt")
        val pageRequest = ServletUtil.getPageRequest(pageParams)
        val currentUser = authenticationFacade.forcedCurrentUser
        val activities = activityViewServices.getActivitiesBy(currentUser, username, pageRequest)
        return ResponseEntity.ok(
            BasicResponseDto.ok(activities.map { ActivityDto(it, ServletUtil.getBaseURLFromCurrentRequest()) })
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
        return ResponseEntity.ok(
            BasicResponseDto.ok(ActivityDto(activity, ServletUtil.getBaseURLFromCurrentRequest()))
        )
    }

}