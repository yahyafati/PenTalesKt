package org.pentales.pentalesrest.services.basic.impl

import org.pentales.pentalesrest.exceptions.*
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
    private val ratingLikeRepository: RatingLikeRepository,
    private val userRepository: UserRepository,
) : IActivityViewServices {

    fun processActivity(activity: ActivityView, currentUser: User) {
        var rating: Rating? = null
        when (activity.type) {
            EActivityType.RATING -> {
                rating = ratingRepository.findById(activity.activityId).orElse(null)
                activity.rating = rating
            }

            EActivityType.COMMENT -> {
                val comment = commentRepository.findById(activity.activityId).orElse(null)
                rating = comment.rating
                activity.comment = comment
            }

            EActivityType.SHARE -> {
                val share = shareRepository.findById(activity.activityId).orElse(null)
                rating = share.rating
                activity.share = share
            }
        }

        if (rating != null) {
            val book = rating.book
            val activityBook = ActivityBook(book)
            activityBook.__averageRating = ratingRepository.findAverageRatingByBook(book) ?: 0.0
            activityBook.__ratingCount = ratingRepository.countAllByBook(book)
            activity.activityBook = activityBook
            rating.user.__isFollowed = followerServices.isFollowing(currentUser, rating.user)
            rating.__likes = ratingLikeRepository.countAllByRating(rating)
            rating.__isLiked = ratingLikeRepository.existsByRatingAndUser(rating, currentUser)
        }
    }

    override fun getActivities(currentUser: User, pageable: Pageable): Page<ActivityView> {
        val followings = followerServices.getFollowings(currentUser)

        val activities = activityViewRepository.findAllByUserIn(
            listOf(
                currentUser,
                *followings.toTypedArray()
            ), pageable
        )
        activities.forEach { processActivity(it, currentUser) }
        return activities
    }

    override fun getActivitiesBy(currentUser: User, username: String, pageable: Pageable): Page<ActivityView> {
        val activities = activityViewRepository.findAllByUserUsername(username, pageable)
        activities.forEach { processActivity(it, currentUser) }
        return activities
    }

    override fun getActivity(user: User, ratingId: Long, activityId: Long?, type: EActivityType): ActivityView {
        val safeType = activityId?.let { type } ?: EActivityType.RATING
        val safeId = if (safeType == EActivityType.RATING) ratingId else activityId!!
        val activity = activityViewRepository.findByActivityIdAndType(safeId, safeType)
            ?: throw NoEntityWithIdException("No activity with id: $safeId and type: $safeType")

        processActivity(activity, user)
        return activity
    }

}