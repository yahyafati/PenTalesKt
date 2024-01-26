package org.pentales.pentalesrest.dto.activity

import com.fasterxml.jackson.annotation.*
import org.pentales.pentalesrest.dto.book.*
import org.pentales.pentalesrest.dto.rating.*
import org.pentales.pentalesrest.dto.ratingComment.*
import org.pentales.pentalesrest.dto.share.*
import org.pentales.pentalesrest.models.enums.*
import org.pentales.pentalesrest.models.view.*

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

    constructor(activityView: ActivityView) : this() {
        id = activityView.id
        activityId = activityView.activityId
        type = activityView.type
        updatedAt = activityView.updatedAt.time
        rating = activityView.getEffectiveRating()?.let { ActivityRatingDto(it) }
        book = activityView.activityBook?.let { ActivityBookDto(it) }
        data = when (type) {
            EActivityType.RATING -> null
            EActivityType.COMMENT -> CommentDto(activityView.comment!!)
            EActivityType.SHARE -> ShareDto(activityView.share!!)
        }
    }
}
