package org.pentales.pentalesrest.dto

import org.pentales.pentalesrest.models.*

class RatingDto(
    var value: Int = 0,
    var review: String = "",
    var userId: Long = 0,
    var bookId: Long = 0,
) {

    constructor(rating: Rating) : this(rating.value, rating.review, rating.user?.id ?: 0, rating.book?.id ?: 0)

    override fun toString(): String {
        return "RatingDto(value=$value, review='$review', userId=$userId, bookId=$bookId)"
    }

}
