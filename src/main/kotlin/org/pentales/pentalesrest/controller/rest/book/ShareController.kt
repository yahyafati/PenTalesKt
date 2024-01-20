package org.pentales.pentalesrest.controller.rest.book

import org.pentales.pentalesrest.dto.share.*
import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.security.*
import org.pentales.pentalesrest.services.basic.*
import org.springframework.http.*
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/share")
class ShareController(
    private val activityShareServices: IShareServices,
    private val authenticationFacade: IAuthenticationFacade,
) {

    @GetMapping
    fun getRatingShare(
        @RequestParam(required = true)
        id: Long
    ): ResponseEntity<ShareDto> {
        val share = activityShareServices.getShareById(id = id)
        return ResponseEntity.ok(ShareDto(share))
    }

    @PostMapping
    fun shareRating(
        @RequestParam(required = true)
        ratingId: Long,
        @RequestBody
        shareDto: AddShareDto
    ): ResponseEntity<ShareDto> {
        val user = authenticationFacade.forcedCurrentUser
        val rating = Rating()
        rating.id = ratingId
        val share = shareDto.toActivityShare(rating = rating, user = user)
        val savedShare = activityShareServices.saveNew(share)
        return ResponseEntity.ok(ShareDto(savedShare))
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