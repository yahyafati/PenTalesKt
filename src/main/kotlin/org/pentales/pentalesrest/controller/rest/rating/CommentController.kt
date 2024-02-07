package org.pentales.pentalesrest.controller.rest.rating

import org.pentales.pentalesrest.controller.rest.*
import org.pentales.pentalesrest.dto.*
import org.pentales.pentalesrest.dto.ratingComment.*
import org.pentales.pentalesrest.dto.report.*
import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.security.*
import org.pentales.pentalesrest.services.basic.*
import org.pentales.pentalesrest.utils.*
import org.springframework.data.domain.*
import org.springframework.http.*
import org.springframework.web.bind.annotation.*
import org.springframework.web.context.request.*
import org.springframework.web.server.*

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
        val request = (RequestContextHolder.getRequestAttributes() as ServletRequestAttributes?)!!.request
        val dto = response.map { CommentDto(it, ServletUtil.getBaseURL(request)) }
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
        val request = (RequestContextHolder.getRequestAttributes() as ServletRequestAttributes?)!!.request
        val dto = CommentDto(savedComment, ServletUtil.getBaseURL(request))
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
        val request = (RequestContextHolder.getRequestAttributes() as ServletRequestAttributes?)!!.request
        val dto = CommentDto(savedComment, ServletUtil.getBaseURL(request))
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
        val request = (RequestContextHolder.getRequestAttributes() as ServletRequestAttributes?)!!.request
        return ResponseEntity.ok(BasicResponseDto.ok(ReportDto(reported, ServletUtil.getBaseURL(request))))
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