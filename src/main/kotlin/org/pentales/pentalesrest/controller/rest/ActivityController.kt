package org.pentales.pentalesrest.controller.rest

import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/activity")
class ActivityController {

//    @GetMapping
//    fun getActivities(
//        @RequestParam
//        page: Int?,
//        @RequestParam
//        size: Int?
//    ): ResponseEntity<BasicResponseDto<Page<ActivityDto>>> {
//        val pageRequest = IBasicControllerSkeleton.getPageRequest(page, size, "createdAt", Sort.Direction.DESC)
//        return ResponseEntity.ok(
//            BasicResponseDto.ok(
//                activityServices.getActivities(pageRequest).map { ActivityDto(it) })
//        )
//    }
//
//    @GetMapping("/{id}")
//    fun getActivityById(
//        @PathVariable
//        id: Long
//    ): ResponseEntity<BasicResponseDto<ActivityDto>> {
//        val dto = ActivityDto(activityServices.getActivityById(id))
//        return ResponseEntity.ok(BasicResponseDto.ok(dto))
//    }

}