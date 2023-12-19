package org.pentales.pentalesrest.dto.activity

import org.pentales.pentalesrest.dto.*
import org.pentales.pentalesrest.models.*

data class ActivityShareDto(
    var quote: String? = "",
    var rating: ActivityRatingDto = ActivityRatingDto(),
    var user: UserDto = UserDto(),
) {

    constructor(activityShare: ActivityShare) : this(
        quote = activityShare.shareQuote,
        rating = ActivityRatingDto(activityShare.rating),
        user = UserDto(activityShare.user),
    )
}
