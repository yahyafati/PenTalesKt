package org.pentales.pentalesrest.controller.rest.book

import org.pentales.pentalesrest.dto.*
import org.pentales.pentalesrest.dto.rating.*
import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.security.*
import org.pentales.pentalesrest.services.basic.*
import org.springframework.http.*
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/rating")
class RatingController(
    private val ratingServices: IRatingServices,
    private val activityShareServices: IShareServices,
    private val authenticationFacade: IAuthenticationFacade,
) {

    companion object {

        const val MAX_PAGE_SIZE = 50
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
        ratingDto: AddRatingDto,
        @RequestParam(defaultValue = "false")
        valueOnly: Boolean? = false
    ): ResponseEntity<BasicResponseDto<RatingDto>> {
        val book = Book(id = bookId)
        val user = authenticationFacade.forcedCurrentUser
        if (valueOnly == true) {
            val savedRating = ratingServices.saveValue(ratingDto.value, book, user)
            return ResponseEntity.ok(BasicResponseDto.ok(RatingDto(savedRating)))
        }
        val rating = ratingDto.toRating(book, user)
        val savedRating = ratingServices.save(rating)
        return ResponseEntity.ok(BasicResponseDto.ok(RatingDto(savedRating)))
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