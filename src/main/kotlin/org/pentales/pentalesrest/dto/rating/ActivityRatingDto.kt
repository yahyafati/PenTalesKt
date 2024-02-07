package org.pentales.pentalesrest.dto.rating

import org.pentales.pentalesrest.dto.user.*
import org.pentales.pentalesrest.models.*

data class ActivityRatingDto(
    val id: Long = 0,
    var value: Int = 0,
    var review: String = "",
    var user: UserDto = UserDto(),
    var likes: Long = 0,
    var isLiked: Boolean = false,
    var createdAt: Long = 0,
    var updatedAt: Long = 0
) {

    constructor(rating: Rating, baseURL: String) : this(
        id = rating.id,
        value = rating.value,
        review = rating.review,
        user = UserDto(rating.user, baseURL),
        likes = rating.__likes,
        isLiked = rating.__isLiked,
        createdAt = rating.createdAt.time,
        updatedAt = rating.updatedAt.time
    )
}
