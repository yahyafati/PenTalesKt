package org.pentales.pentalesrest.dto.activity

import org.pentales.pentalesrest.dto.*
import org.pentales.pentalesrest.models.*

data class ActivityRatingDto(
    var user: UserDto = UserDto(),
    var book: BookDTO = BookDTO(),
    var value: Int = 3,
    val review: String = "",
    val commentsCount: Int? = 0,
) {

    constructor(rating: Rating) : this(
        user = UserDto(rating.user),
        book = BookDTO(rating.book),
        value = rating.value,
        review = rating.review,
        commentsCount = rating.comments.size
    )
}