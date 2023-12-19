package org.pentales.pentalesrest.dto.activity

import org.pentales.pentalesrest.dto.*
import org.pentales.pentalesrest.models.*

data class ActivityRatingDto(
    var value: Int = 3,
    val review: String = "",
    val commentsCount: Int? = 0,
) {

    constructor(rating: Rating) : this(
        value = rating.value,
        review = rating.review,
        commentsCount = rating.comments.size,
    )
}