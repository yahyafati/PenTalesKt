package org.pentales.pentalesrest.models.entities.activity.dto

import com.fasterxml.jackson.annotation.*
import org.pentales.pentalesrest.models.entities.activity.*
import org.pentales.pentalesrest.models.entities.book.dto.*
import org.pentales.pentalesrest.models.entities.rating.comment.dtos.*
import org.pentales.pentalesrest.models.entities.rating.dto.*
import org.pentales.pentalesrest.models.entities.rating.share.dto.*

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ActivityDto(
    var id: Long = 0,
    var activityId: Long = 0,
    var type: EActivityType = EActivityType.RATING,
    var updatedAt: Long = System.currentTimeMillis(),
    var rating: ActivityRatingDto? = ActivityRatingDto(),
    var book: ActivityBookDto? = ActivityBookDto(),
    var data: Any? = null,
) {

    constructor(activityView: ActivityView, baseURL: String) : this() {
        id = activityView.ratingId
        activityId = activityView.activityId
        type = activityView.type
        updatedAt = activityView.updatedAt.time
        rating = activityView.getEffectiveRating()?.let { ActivityRatingDto(it, baseURL) }
        book = activityView.activityBook?.let { ActivityBookDto(it, baseURL) }
        data = when (type) {
            EActivityType.RATING -> null
            EActivityType.COMMENT -> CommentDto(activityView.comment!!, baseURL)
            EActivityType.SHARE -> ShareDto(activityView.share!!, baseURL)
        }
    }
}
