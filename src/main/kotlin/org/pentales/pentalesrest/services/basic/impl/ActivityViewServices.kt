package org.pentales.pentalesrest.services.basic.impl

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
) : IActivityViewServices {

    override fun getActivities(pageable: Pageable): Page<ActivityView> {
        val activities = activityViewRepository.findAll(pageable)
        activities.forEach { activity ->
            when (activity.type) {
                EActivityType.RATING -> {
                    activity.rating = ratingRepository.findById(activity.id).orElse(null)
                }

                EActivityType.COMMENT -> {
                    activity.comment = commentRepository.findById(activity.id).orElse(null)
                }

                EActivityType.SHARE -> {
                    activity.share = shareRepository.findById(activity.id).orElse(null)
                }
            }
        }


        return activities
    }
}