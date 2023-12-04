package org.pentales.pentalesrest.controller.rest.book

import org.pentales.pentalesrest.controller.rest.*
import org.pentales.pentalesrest.dto.*
import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.models.keys.*
import org.pentales.pentalesrest.security.*
import org.pentales.pentalesrest.services.basic.*
import org.springframework.data.domain.*
import org.springframework.http.*
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/book/{bookId}/rating")
class RatingController(
    private val ratingServices: IRatingServices,
    private val ratingCommentServices: IRatingCommentServices,
    private val activityShareServices: IActivityShareServices,
    private val authenticationFacade: IAuthenticationFacade,
) {

    companion object {

        const val MAX_PAGE_SIZE = 50
    }

    @GetMapping("")
    fun getAll(
        @PathVariable
        bookId: Long,
        @RequestParam(defaultValue = "0")
        page: Int?,
        @RequestParam(defaultValue = "10")
        size: Int?,
        @RequestParam(defaultValue = "")
        sort: String?,
        @RequestParam(defaultValue = "ASC")
        direction: Sort.Direction?,
    ): ResponseEntity<Page<RatingDto>> {

        val pageRequest = IBasicControllerSkeleton.getPageRequest(page, size, sort, direction)
        val response = ratingServices.findByBookId(bookId, pageRequest).map { RatingDto(it) }
        return ResponseEntity.ok(response)
    }

    @GetMapping("/{userId}")
    fun getRatingById(
        @PathVariable
        bookId: Long,
        @PathVariable
        userId: Long
    ): ResponseEntity<RatingDto> {
        val rating = ratingServices.findById(bookId = bookId, userId = userId)
        return ResponseEntity.ok(RatingDto(rating))
    }

    @PostMapping("")
    fun saveRating(
        @PathVariable
        bookId: Long,
        @RequestBody
        ratingDto: AddRatingDto
    ): ResponseEntity<RatingDto> {
        val book = Book(id = bookId)
        val user = authenticationFacade.currentUserMust
        val rating = ratingDto.toRating(book, user)
        val savedRating = ratingServices.saveNew(rating)
        return ResponseEntity.ok(RatingDto(savedRating))
    }

    @DeleteMapping("/{userId}")
    fun deleteRating(
        @PathVariable
        bookId: Long,
        @PathVariable
        userId: Long
    ): ResponseEntity<Unit> {
        ratingServices.deleteById(bookId = bookId, userId = userId)
        return ResponseEntity.ok().build()
    }

    @GetMapping("/{userId}/comments")
    fun getRatingComments(
        @PathVariable
        bookId: Long,
        @PathVariable
        userId: Long,
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
        val response = ratingCommentServices.findAllByRating(
            rating = Rating(id = UserBookKey(bookId = bookId, userId = userId)),
            pageable = pageRequest,
        ).map { RatingCommentDto(it) }
        return ResponseEntity.ok(response)
    }

    @PostMapping("/{userId}/comments")
    fun saveRatingComment(
        @PathVariable
        bookId: Long,
        @PathVariable
        userId: Long,
        @RequestBody
        ratingCommentDto: AddRatingCommentDto
    ): ResponseEntity<RatingCommentDto> {
        val user = authenticationFacade.currentUserMust
        val rating = Rating(id = UserBookKey(bookId = bookId, userId = userId))
        val ratingComment = ratingCommentDto.toRatingComment(user, rating)
        val savedRatingComment = ratingCommentServices.saveNew(ratingComment)
        return ResponseEntity.ok(RatingCommentDto(savedRatingComment))
    }

    @DeleteMapping("/{userId}/comments/{commentId}")
    fun deleteRatingComment(
        @PathVariable
        commentId: Long
    ): ResponseEntity<Unit> {
        ratingCommentServices.deleteById(id = commentId)
        return ResponseEntity.ok().build()
    }

    @GetMapping("/{userId}/share/{shareId}")
    fun getRatingShare(
        @PathVariable
        shareId: Long
    ): ResponseEntity<ShareDto> {
        val share = activityShareServices.getShareById(id = shareId)
        return ResponseEntity.ok(ShareDto(share))
    }

    @PostMapping("/{userId}/share")
    fun shareRating(
        @PathVariable
        bookId: Long,
        @PathVariable
        userId: Long,
        @RequestBody
        shareDto: AddShareDto
    ): ResponseEntity<ShareDto> {
        val user = authenticationFacade.currentUserMust
        val rating = Rating(id = UserBookKey(bookId = bookId, userId = userId))
        val share = shareDto.toActivityShare(rating = rating, user = user)
        val savedShare = activityShareServices.saveNew(share)
        return ResponseEntity.ok(ShareDto(savedShare))
    }

    @DeleteMapping("/{userId}/share/{shareId}")
    fun unshareRating(
        @PathVariable
        shareId: Long
    ): ResponseEntity<Unit> {
        activityShareServices.deleteById(id = shareId)
        return ResponseEntity.ok().build()
    }

}