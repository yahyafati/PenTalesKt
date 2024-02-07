package org.pentales.pentalesrest.controller.rest.rating

import org.pentales.pentalesrest.dto.share.*
import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.security.*
import org.pentales.pentalesrest.services.basic.*
import org.pentales.pentalesrest.utils.*
import org.springframework.http.*
import org.springframework.web.bind.annotation.*
import org.springframework.web.context.request.*

@RestController
@RequestMapping("/api/share")
class ShareController(
    private val activityShareServices: IShareServices,
    private val authenticationFacade: IAuthenticationFacade,
) {

    @GetMapping("/{id}")
    fun getRatingShare(
        @PathVariable
        id: Long
    ): ResponseEntity<ShareDto> {
        val share = activityShareServices.getShareById(id = id)
        val request = (RequestContextHolder.getRequestAttributes() as ServletRequestAttributes?)!!.request
        return ResponseEntity.ok(ShareDto(share, ServletUtil.getBaseURL(request)))
    }

    @PostMapping("/rating/{ratingId}")
    fun shareRating(
        @PathVariable
        ratingId: Long,
        @RequestBody(required = false)
        shareDto: AddShareDto?
    ): ResponseEntity<ShareDto> {
        val user = authenticationFacade.forcedCurrentUser
        val rating = Rating(id = ratingId)

        val share = shareDto?.toActivityShare(rating = rating, user = user) ?: Share(
            rating = rating,
            user = user,
        )
        val savedShare = activityShareServices.saveNew(share)
        val request = (RequestContextHolder.getRequestAttributes() as ServletRequestAttributes?)!!.request
        return ResponseEntity.ok(ShareDto(savedShare, ServletUtil.getBaseURL(request)))
    }

    @DeleteMapping("/{shareId}")
    fun unshareRating(
        @PathVariable
        shareId: Long
    ): ResponseEntity<Unit> {
        activityShareServices.deleteById(id = shareId)
        return ResponseEntity.ok().build()
    }
}