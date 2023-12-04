package org.pentales.pentalesrest.services.basic.impl

import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.repo.*
import org.pentales.pentalesrest.services.basic.*
import org.springframework.stereotype.*

@Service
class ActivityService(
    val activityRepository: ActivityRepository
) : IActivityServices {

    override fun saveRating(rating: Rating): Activity {
        val activity = Activity(rating = rating)
        return activityRepository.save(activity)
    }

    override fun saveComment(comment: RatingComment): Activity {
        val activity = Activity(ratingComment = comment)
        return activityRepository.save(activity)
    }

    override fun saveShare(share: ActivityShare): Activity {
        val activity = Activity(share = share)
        return activityRepository.save(activity)
    }

    override fun deleteActivity(id: Long) {
        activityRepository.deleteById(id)
    }
}