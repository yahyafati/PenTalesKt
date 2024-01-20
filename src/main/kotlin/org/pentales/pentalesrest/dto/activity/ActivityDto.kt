package org.pentales.pentalesrest.dto.activity

import com.fasterxml.jackson.annotation.*
import org.pentales.pentalesrest.dto.rating.*
import org.pentales.pentalesrest.dto.ratingComment.*
import org.pentales.pentalesrest.dto.share.*
import org.pentales.pentalesrest.models.enums.*
import org.pentales.pentalesrest.models.view.*

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ActivityDto(
    var id: Long = 0,
    var type: EActivityType = EActivityType.RATING,
    var updatedAt: Long = System.currentTimeMillis(),
    var rating: RatingDto? = RatingDto(),
    var data: Any? = null,
) {

    constructor(activityView: ActivityView) : this() {
        id = activityView.id
        type = activityView.type
        updatedAt = activityView.updatedAt.time
        rating = activityView.getEffectiveRating()?.let { RatingDto(it) }
        data = when (type) {
            EActivityType.RATING -> null
            EActivityType.COMMENT -> CommentDto(activityView.comment!!)
            EActivityType.SHARE -> ShareDto(activityView.share!!)
        }
    }
}
