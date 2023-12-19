package org.pentales.pentalesrest.controller.rest.book

import org.pentales.pentalesrest.controller.rest.*
import org.pentales.pentalesrest.dto.*
import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.security.*
import org.pentales.pentalesrest.services.basic.*
import org.springframework.data.domain.*
import org.springframework.http.*
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/comment")
class RatingCommentController(
    private val ratingCommentServices: IRatingCommentServices,
    private val authenticationFacade: IAuthenticationFacade,
) {

    @GetMapping("/comments")
    fun getRatingComments(
        @RequestParam(required = true)
        ratingId: Long,
        @RequestParam(defaultValue = "0")
        page: Int?,
        @RequestParam(defaultValue = "10")
        size: Int?,
        @RequestParam(defaultValue = "")
        sort: String?,
        @RequestParam(defaultValue = "ASC")
        direction: Sort.Direction?,
    ): ResponseEntity<Page<RatingCommentDto>> {
        val pageRequest = IBasicControllerSkeleton.getPageRequest(page, size, sort, direction)
        val rating = Rating()
        rating.id = ratingId
        val response = ratingCommentServices.findAllByRating(
            rating = rating,
            pageable = pageRequest,
        ).map { RatingCommentDto(it) }
        return ResponseEntity.ok(response)
    }

    @PostMapping("/comments")
    fun saveRatingComment(
        @RequestParam(required = true)
        ratingId: Long,
        @RequestBody
        ratingCommentDto: AddRatingCommentDto
    ): ResponseEntity<RatingCommentDto> {
        val user = authenticationFacade.forcedCurrentUser
        val rating = Rating()
        rating.id = ratingId
        val ratingComment = ratingCommentDto.toRatingComment(user, rating)
        val savedRatingComment = ratingCommentServices.saveNew(ratingComment)
        return ResponseEntity.ok(RatingCommentDto(savedRatingComment))
    }

    @DeleteMapping("/comments")
    fun deleteRatingComment(
        @RequestParam(required = true)
        commentId: Long
    ): ResponseEntity<Unit> {
        ratingCommentServices.deleteById(id = commentId)
        return ResponseEntity.ok().build()
    }
}