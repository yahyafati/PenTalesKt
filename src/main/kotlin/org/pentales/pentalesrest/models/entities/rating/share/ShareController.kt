package org.pentales.pentalesrest.models.entities.rating.share

import org.pentales.pentalesrest.config.security.*
import org.pentales.pentalesrest.models.entities.rating.share.dto.*
import org.pentales.pentalesrest.utils.*
import org.springframework.http.*
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/share")
class ShareController(
    private val ratingServices: org.pentales.pentalesrest.models.entities.rating.IRatingServices,
    private val activityShareServices: IShareServices,
    private val authenticationFacade: IAuthenticationFacade,
) {

    @GetMapping("/{id}")
    fun getRatingShare(
        @PathVariable
        id: Long
    ): ResponseEntity<ShareDto> {
        val share = activityShareServices.getShareById(id = id)
        return ResponseEntity.ok(ShareDto(share, ServletUtil.getBaseURLFromCurrentRequest()))
    }

    @PostMapping("/rating/{ratingId}")
    fun shareRating(
        @PathVariable
        ratingId: Long,
        @RequestBody(required = false)
        shareDto: AddShareDto?
    ): ResponseEntity<ShareDto> {
        val user = authenticationFacade.forcedCurrentUser
        val rating = ratingServices.findById(ratingId)

        val share = shareDto?.toActivityShare(rating = rating, user = user) ?: Share(
            rating = rating,
            user = user,
        )
        val savedShare = activityShareServices.saveNew(share)
        return ResponseEntity.ok(ShareDto(savedShare, ServletUtil.getBaseURLFromCurrentRequest()))
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