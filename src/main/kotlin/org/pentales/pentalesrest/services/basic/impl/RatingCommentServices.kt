package org.pentales.pentalesrest.services.basic.impl

import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.repo.*
import org.pentales.pentalesrest.services.basic.*
import org.springframework.data.domain.*
import org.springframework.stereotype.*

@Service
class RatingCommentServices(
    private val ratingCommentRepository: RatingCommentRepository,
    private val activityRepository: ActivityRepository,
) : IRatingCommentServices {

    override fun findAllByRating(rating: Rating, pageable: Pageable): Page<RatingComment> {
        return ratingCommentRepository.findAllByRating(rating, pageable)
    }

    override fun countAllByRating(rating: Rating): Long {
        return ratingCommentRepository.countAllByRating(rating)
    }

    override fun save(ratingComment: RatingComment): RatingComment {
        return ratingCommentRepository.save(ratingComment)
    }

    override fun saveNew(ratingComment: RatingComment): RatingComment {
        ratingComment.id = 0
        val savedComment = ratingCommentRepository.save(ratingComment)
        val activity = Activity(ratingComment = savedComment)
        activityRepository.save(activity)
        return savedComment
    }

    override fun deleteById(id: Long) {
        val comment = RatingComment()
        comment.id = id
        val affected = activityRepository.deleteByRatingComment(comment)
        if (affected == 0L) {
            ratingCommentRepository.deleteById(id)
        }
    }

}