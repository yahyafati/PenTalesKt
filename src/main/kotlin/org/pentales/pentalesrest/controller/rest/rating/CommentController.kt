package org.pentales.pentalesrest.controller.rest.rating

import org.pentales.pentalesrest.controller.rest.IBasicControllerSkeleton
import org.pentales.pentalesrest.dto.BasicResponseDto
import org.pentales.pentalesrest.dto.dto.AddReportDto
import org.pentales.pentalesrest.dto.dto.ReportDto
import org.pentales.pentalesrest.dto.ratingComment.AddCommentDto
import org.pentales.pentalesrest.dto.ratingComment.CommentDto
import org.pentales.pentalesrest.models.Comment
import org.pentales.pentalesrest.models.Rating
import org.pentales.pentalesrest.models.User
import org.pentales.pentalesrest.security.IAuthenticationFacade
import org.pentales.pentalesrest.services.basic.ICommentServices
import org.pentales.pentalesrest.services.basic.IReportServices
import org.springframework.data.domain.Page
import org.springframework.data.domain.Sort
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RequestMapping("/api/comment")
@RestController
class CommentController(
    private val commentServices: ICommentServices,
    private val reportServices: IReportServices,
    private val authenticationFacade: IAuthenticationFacade,
) {

    companion object {

        private fun validateAccess(comment: Comment, user: User) {
            if (comment.user.id != user.id) {
                throw ResponseStatusException(HttpStatus.FORBIDDEN, "Comment not accessible")
            }
        }
    }

    @GetMapping("/rating/{ratingId}")
    fun getRatingComments(
        @PathVariable
        ratingId: Long,
        @RequestParam(defaultValue = "0")
        page: Int?,
        @RequestParam(defaultValue = "10")
        size: Int?,
        @RequestParam(defaultValue = "updatedAt")
        sort: String?,
        @RequestParam(defaultValue = "DESC")
        direction: Sort.Direction?,
    ): ResponseEntity<BasicResponseDto<Page<CommentDto>>> {
        val rating = Rating(id = ratingId)

        val pageRequest = IBasicControllerSkeleton.getPageRequest(page, size, sort, Sort.Direction.DESC)
        val response = commentServices.findAllByRating(rating, pageRequest)
        val dto = response.map { CommentDto(it) }
        return ResponseEntity.ok(BasicResponseDto.ok(dto))
    }

    @PostMapping("/rating/{ratingId}")
    fun addRatingComment(
        @PathVariable
        ratingId: Long,
        @RequestBody
        addCommentDto: AddCommentDto
    ): ResponseEntity<BasicResponseDto<CommentDto>> {
        val user = authenticationFacade.forcedCurrentUser
        val rating = Rating(id = ratingId)
        val comment = addCommentDto.toComment(rating = rating, user = user)
        val savedComment = commentServices.saveNew(comment)
        val dto = CommentDto(savedComment)
        return ResponseEntity.ok(BasicResponseDto.ok(dto))
    }

    @PutMapping("/{commentId}")
    fun updateComment(
        @PathVariable
        commentId: Long,
        @RequestBody
        updateCommentDto: AddCommentDto
    ): ResponseEntity<BasicResponseDto<CommentDto>> {
        val user = authenticationFacade.forcedCurrentUser
        val comment = commentServices.getCommentById(id = commentId)
        validateAccess(comment, user)
        val updatedComment = updateCommentDto.toComment(rating = comment.rating, user = user)
        updatedComment.id = commentId
        val savedComment = commentServices.save(updatedComment)
        val dto = CommentDto(savedComment)
        return ResponseEntity.ok(BasicResponseDto.ok(dto))
    }

    @PatchMapping("/{commentId}/report")
    fun reportComment(
        @PathVariable
        commentId: Long,
        @RequestBody
        reportDto: AddReportDto
    ): ResponseEntity<BasicResponseDto<ReportDto>> {
        val user = authenticationFacade.forcedCurrentUser
        val report = reportDto.toReport(user = user, commentId = commentId, ratingId = null)
        val reported = reportServices.saveNew(report)
        return ResponseEntity.ok(BasicResponseDto.ok(ReportDto(reported)))
    }

    @DeleteMapping("/{commentId}")
    fun deleteComment(
        @PathVariable
        commentId: Long
    ): ResponseEntity<BasicResponseDto<Long>> {
        val user = authenticationFacade.forcedCurrentUser
        val deleted = commentServices.deleteById(id = commentId, user = user)
        return ResponseEntity.ok(BasicResponseDto.ok(deleted))
    }

}