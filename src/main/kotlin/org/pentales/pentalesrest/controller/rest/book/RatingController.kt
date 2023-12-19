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
@RequestMapping("/api/rating")
class RatingController(
    private val ratingServices: IRatingServices,
    private val activityShareServices: IActivityShareServices,
    private val authenticationFacade: IAuthenticationFacade,
) {

    companion object {

        const val MAX_PAGE_SIZE = 50
    }

    @GetMapping("/{bookId}")
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

    @GetMapping("/{id}")
    fun getRatingById(
        @PathVariable
        id: Long
    ): ResponseEntity<RatingDto> {
        val rating = ratingServices.findById(id)
        return ResponseEntity.ok(RatingDto(rating))
    }

    @PostMapping("/{bookId}")
    fun saveRating(
        @PathVariable
        bookId: Long,
        @RequestBody
        ratingDto: AddRatingDto
    ): ResponseEntity<RatingDto> {
        val book = Book(id = bookId)
        val user = authenticationFacade.forcedCurrentUser
        val rating = ratingDto.toRating(book, user)
        val savedRating = ratingServices.saveNew(rating)
        return ResponseEntity.ok(RatingDto(savedRating))
    }

    @DeleteMapping("/{id}")
    fun deleteRating(
        @PathVariable
        id: Long
    ): ResponseEntity<Unit> {
        ratingServices.deleteById(id)
        return ResponseEntity.ok().build()
    }

}