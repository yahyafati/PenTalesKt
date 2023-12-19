package org.pentales.pentalesrest.dto.activity

import org.pentales.pentalesrest.dto.*
import org.pentales.pentalesrest.models.*

data class ActivityDto(
    var id: Long = 0,
    var rating: ActivityRatingDto = ActivityRatingDto(),
    var user: UserDto = UserDto(),
    var book: BookDTO = BookDTO(),
    var ratingComment: ActivityCommentDto? = null,
    var share: ActivityShareDto? = null,
    var activityType: EActivityType = EActivityType.RATING,
    var createdAt: Long = 0,
) {

    constructor(activity: Activity) : this() {
        this.id = activity.id
        val rating = activity.rating ?: activity.ratingComment?.rating ?: activity.share?.rating
        if (rating == null) {
            throw NullPointerException("Invalid Activity. Rating is null for all activity types. Can't resolve activity type.")
        }
        this.rating = ActivityRatingDto(rating)
        this.user = UserDto(rating.user)
        this.book = BookDTO(rating.book)
        this.ratingComment = activity.ratingComment?.let { ActivityCommentDto(it) }
        this.share = activity.share?.let { ActivityShareDto(it) }
        this.createdAt = activity.createdAt.time

        this.activityType = EActivityType.RATING
        if (activity.rating != null) {
            this.activityType = EActivityType.RATING
        } else if (activity.ratingComment != null) {
            this.activityType = EActivityType.COMMENT
        } else if (activity.share != null) {
            this.activityType = EActivityType.SHARE
        }
    }

}