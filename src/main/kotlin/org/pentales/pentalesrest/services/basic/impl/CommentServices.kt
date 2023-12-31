package org.pentales.pentalesrest.services.basic.impl

import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.repo.*
import org.pentales.pentalesrest.services.basic.*
import org.springframework.data.domain.*
import org.springframework.stereotype.*

@Service
class CommentServices(
    private val commentRepository: CommentRepository,
    private val activityRepository: ActivityRepository,
) : ICommentServices {

    override fun findAllByRating(rating: Rating, pageable: Pageable): Page<Comment> {
        return commentRepository.findAllByRating(rating, pageable)
    }

    override fun countAllByRating(rating: Rating): Long {
        return commentRepository.countAllByRating(rating)
    }

    override fun save(comment: Comment): Comment {
        return commentRepository.save(comment)
    }

    override fun saveNew(comment: Comment): Comment {
        comment.id = 0
        val savedComment = commentRepository.save(comment)
        val activity = Activity(comment = savedComment)
        activityRepository.save(activity)
        return savedComment
    }

    override fun deleteById(id: Long) {
        val comment = Comment()
        comment.id = id
        val affected = activityRepository.deleteByComment(comment)
        if (affected == 0L) {
            commentRepository.deleteById(id)
        }
    }

}