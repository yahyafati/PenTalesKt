package org.pentales.pentalesrest.controller.rest.book

import org.pentales.pentalesrest.dto.*
import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.security.*
import org.pentales.pentalesrest.services.basic.*
import org.springframework.data.domain.*
import org.springframework.http.*
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/book/{bookId}/rating")
class RatingController(private val service: IRatingServices, private val authenticationFacade: IAuthenticationFacade) {

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
        val pageNumber = page ?: 0
        val pageSize = size ?: 10
        val sortDirection = direction ?: Sort.Direction.ASC

        val pageRequest = if (sort.isNullOrEmpty()) {
            PageRequest.of(pageNumber, pageSize.coerceAtMost(MAX_PAGE_SIZE))
        } else {
            PageRequest.of(pageNumber, pageSize.coerceAtMost(MAX_PAGE_SIZE), Sort.by(sortDirection, sort))
        }
        return ResponseEntity.ok(service.findByBookId(bookId, pageRequest).map { RatingDto(it) })
    }

    @GetMapping("/{userId}")
    fun getRatingById(
        @PathVariable
        bookId: Long,
        @PathVariable
        userId: Long
    ): ResponseEntity<RatingDto> {
        val rating = service.findById(bookId = bookId, userId = userId)
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
        val savedRating = service.save(rating)
        return ResponseEntity.ok(RatingDto(savedRating))
    }

    @DeleteMapping("/{userId}")
    fun deleteRating(
        @PathVariable
        bookId: Long,
        @PathVariable
        userId: Long
    ): ResponseEntity<Unit> {
        service.deleteById(bookId = bookId, userId = userId)
        return ResponseEntity.ok().build()
    }

}