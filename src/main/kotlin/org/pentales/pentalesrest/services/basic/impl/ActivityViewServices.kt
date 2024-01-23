package org.pentales.pentalesrest.services.basic.impl

import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.models.enums.*
import org.pentales.pentalesrest.models.view.*
import org.pentales.pentalesrest.repo.*
import org.pentales.pentalesrest.services.basic.*
import org.springframework.data.domain.*
import org.springframework.stereotype.*

@Service
class ActivityViewServices(
    private val activityViewRepository: ActivityViewRepository,
    private val shareRepository: ShareRepository,
    private val ratingRepository: RatingRepository,
    private val commentRepository: CommentRepository,
    private val followerServices: IFollowerServices,
) : IActivityViewServices {

    override fun getActivities(currentUser: User, pageable: Pageable): Page<ActivityView> {
        val activities = activityViewRepository.findAll(pageable)
        activities.forEach { activity ->
            var rating: Rating? = null
            when (activity.type) {
                EActivityType.RATING -> {
                    rating = ratingRepository.findById(activity.id).orElse(null)
                    activity.rating = rating
                }

                EActivityType.COMMENT -> {
                    val comment = commentRepository.findById(activity.id).orElse(null)
                    rating = comment.rating
                    activity.comment = comment
                }

                EActivityType.SHARE -> {
                    val share = shareRepository.findById(activity.id).orElse(null)
                    rating = share.rating
                    activity.share = share
                }
            }

            if (rating != null) {
                val book = rating.book
                val averageRating = ratingRepository.findAverageRatingByBook(book) ?: 0.0
                book.__averageRating = averageRating
                rating.user.__isFollowed = followerServices.isFollowing(currentUser, rating.user)
            }

        }


        return activities
    }
}