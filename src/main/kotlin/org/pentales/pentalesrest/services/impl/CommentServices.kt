package org.pentales.pentalesrest.services.impl

import com.google.firebase.messaging.*
import org.pentales.pentalesrest.dto.user.*
import org.pentales.pentalesrest.exceptions.*
import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.repo.*
import org.pentales.pentalesrest.utils.*
import org.springframework.data.domain.*
import org.springframework.stereotype.*
import org.springframework.transaction.annotation.*

@Service
class CommentServices(
    private val repository: CommentRepository,
    private val pushNotificationService: PushNotificationService,
) : org.pentales.pentalesrest.services.ICommentServices {

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
        return repository.save(comment)
    }

    override fun saveNew(comment: Comment): Comment {
        comment.id = 0
        val saved = save(comment)
        if (saved.user.id != saved.rating.user.id) {
            val notification = Notification.builder()
                .setTitle("New Comment")
                .setBody("${saved.user.username} commented on your review of ${saved.rating.book.title}")
                .setImage(
                    UserDto.getURLWithBaseURL(
                        saved.user.profile?.profilePicture,
                        ServletUtil.getBaseURLFromCurrentRequest()
                    )
                )
                .build()

            pushNotificationService.sendPushNotificationToUser(notification, saved.rating.user.id)
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