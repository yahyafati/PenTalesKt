package org.pentales.pentalesrest.dto

import org.pentales.pentalesrest.models.enums.*
import org.pentales.pentalesrest.models.view.*

data class ActivityDto(
    var id: Long = 0,
    var type: EActivityType = EActivityType.RATING,
    var updatedAt: Long = System.currentTimeMillis(),
    var data: Any? = null,
) {

    constructor(activityView: ActivityView) : this(

        id = activityView.id,
        type = activityView.type,
        updatedAt = activityView.updatedAt.time,
        data = when (activityView.type) {
            EActivityType.RATING -> activityView.rating?.let { RatingDto(it) }
            EActivityType.COMMENT -> activityView.comment?.let { CommentDto(it) }
            EActivityType.SHARE -> activityView.share?.let { ShareDto(it) }
        }

    )
}
