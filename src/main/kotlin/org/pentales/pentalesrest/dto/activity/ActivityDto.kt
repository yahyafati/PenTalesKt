package org.pentales.pentalesrest.dto.activity

import org.pentales.pentalesrest.models.*

data class ActivityDto(
    var ratingDto: ActivityRatingDto? = null,
    var ratingCommentDto: ActivityCommentDto? = null,
    var shareDto: ActivityShareDto? = null,
) {

    constructor(activity: Activity) : this(
        ratingDto = activity.rating?.let { ActivityRatingDto(it) },
        ratingCommentDto = activity.ratingComment?.let { ActivityCommentDto(it) },
        shareDto = activity.share?.let { ActivityShareDto(it) },
    )
}