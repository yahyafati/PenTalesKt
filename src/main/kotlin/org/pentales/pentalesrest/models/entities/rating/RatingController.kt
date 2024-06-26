package org.pentales.pentalesrest.models.entities.rating

import org.pentales.pentalesrest.config.security.*
import org.pentales.pentalesrest.global.dto.*
import org.pentales.pentalesrest.models.entities.rating.dto.*
import org.pentales.pentalesrest.models.entities.report.dto.*
import org.pentales.pentalesrest.utils.*
import org.springframework.http.*
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/rating")
class RatingController(
    private val ratingServices: IRatingServices,
    private val reportServices: org.pentales.pentalesrest.models.entities.report.IReportServices,
    private val authenticationFacade: IAuthenticationFacade,
) {

    @GetMapping("/{id}")
    fun getRatingById(
        @PathVariable
        id: Long
    ): ResponseEntity<RatingDto> {
        val rating = ratingServices.findById(id)
        return ResponseEntity.ok(RatingDto(rating, ServletUtil.getBaseURLFromCurrentRequest()))
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
        val book = org.pentales.pentalesrest.models.entities.book.Book(id = bookId)
        val user = authenticationFacade.forcedCurrentUser
        if (valueOnly == true) {
            val savedRating = ratingServices.saveValue(ratingDto.value, book, user)
            return ResponseEntity.ok(
                BasicResponseDto.ok(
                    RatingDto(
                        savedRating,
                        ServletUtil.getBaseURLFromCurrentRequest()
                    )
                )
            )
        }
        val rating = ratingDto.toRating(book, user)
        val savedRating = ratingServices.saveNew(rating)
        return ResponseEntity.ok(
            BasicResponseDto.ok(RatingDto(savedRating, ServletUtil.getBaseURLFromCurrentRequest()))
        )
    }

    @PatchMapping("/{id}/like")
    fun likeRating(
        @PathVariable
        id: Long
    ): ResponseEntity<BasicResponseDto<Boolean>> {
        val user = authenticationFacade.forcedCurrentUser
        val rating = ratingServices.findById(id)
        val like = ratingServices.likeRating(rating, user)
        return ResponseEntity.ok(BasicResponseDto.ok(like))
    }

    @PatchMapping("/{id}/unlike")
    fun unlikeRating(
        @PathVariable
        id: Long
    ): ResponseEntity<BasicResponseDto<Boolean>> {
        val user = authenticationFacade.forcedCurrentUser
        val like = ratingServices.unlikeRating(Rating(id = id), user)
        return ResponseEntity.ok(BasicResponseDto.ok(like))
    }

    @PatchMapping("/{id}/report")
    fun reportRating(
        @PathVariable
        id: Long,
        @RequestBody
        reportDto: AddReportDto
    ): ResponseEntity<BasicResponseDto<ReportDto>> {
        val user = authenticationFacade.forcedCurrentUser
        val report = reportDto.toReport(ratingId = id, user = user, commentId = null)
        val savedReport = reportServices.saveNew(report)
        return ResponseEntity.ok(
            BasicResponseDto.ok(ReportDto(savedReport, ServletUtil.getBaseURLFromCurrentRequest()))
        )
    }

    @DeleteMapping("/{id}")
    fun deleteRating(
        @PathVariable
        id: Long
    ): ResponseEntity<Unit> {
        val user = authenticationFacade.forcedCurrentUser
        ratingServices.deleteById(id, user)
        return ResponseEntity.ok().build()
    }

}