package org.pentales.pentalesrest.models.entities.rating.comment

import org.pentales.pentalesrest.config.properties.*
import org.pentales.pentalesrest.exceptions.*
import org.pentales.pentalesrest.models.entities.rating.*
import org.pentales.pentalesrest.models.entities.user.*
import org.pentales.pentalesrest.models.entities.user.dto.*
import org.pentales.pentalesrest.models.misc.pushNotification.*
import org.pentales.pentalesrest.models.misc.sentimentAnalysis.*
import org.pentales.pentalesrest.utils.*
import org.springframework.data.domain.*
import org.springframework.stereotype.*
import org.springframework.transaction.annotation.*

@Service
class CommentServices(
    private val repository: CommentRepository,
    private val pushNotificationService: PushNotificationService,
    private val sentimentAnalysisService: SentimentAnalysisService,
) : ICommentServices {

    override fun getCommentById(id: Long): Comment {
        return repository.findById(id).orElseThrow { NoEntityWithIdException.create("Comment", id) }
    }

    override fun findAllByRating(rating: Rating, pageable: Pageable): Page<Comment> {
        return repository.findAllByRating(rating, pageable)
    }

    override fun countAllByRating(rating: Rating): Long {
        return repository.countAllByRating(rating)
    }

    override fun save(comment: Comment): Comment {
        val saved = repository.save(comment)
        sentimentAnalysisService.addRequest(
            SentimentAnalysisRequest(
                ESentimentAnalysisRequestType.COMMENT,
                saved.id,
                saved.comment
            )
        )
        return saved
    }

    override fun saveNew(comment: Comment): Comment {
        comment.id = 0
        val saved = save(comment)
        if (saved.user.id != saved.rating.user.id) {
            pushNotificationService.sendPushNotificationToUser(
                IPushNotificationService.ActionType.OPEN_REVIEW_COMMENT,
                saved.rating.user.id,
                mapOf(
                    "type" to "comment",
                    "commentId" to saved.id.toString(),
                    "reviewId" to saved.rating.id.toString(),
                    "bookId" to saved.rating.book.id.toString(),
                    "bookTitle" to saved.rating.book.title,
                    "username" to saved.user.username,
                    "icon" to UserDto.getURLWithBaseURL(
                        saved.user.profile?.profilePicture,
                        ServletUtil.getBaseURLFromCurrentRequest()
                    ).let { it ?: "" }
                )
            )
        }
        return saved
    }

    @Transactional
    override fun deleteById(id: Long, user: User): Long {
        return repository.deleteCommentByIdAndUser(id, user)
    }

    override fun updateComment(comment: Comment): Comment {
        TODO("Not yet implemented")
    }

}