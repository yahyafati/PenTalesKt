package org.pentales.pentalesrest.dto

import org.pentales.pentalesrest.models.*

class RatingDto(
    var value: Int = 0,
    var review: String = "",
    var userId: Long = 0,
    var bookId: Long = 0,
    var createdAt: Long = 0,
    var updatedAt: Long = 0
) {

    constructor(rating: Rating) : this(
        value = rating.value,
        review = rating.review,
        userId = rating.user?.id ?: 0,
        bookId = rating.book?.id ?: 0,
        createdAt = rating.createdAt.time,
        updatedAt = rating.updatedAt.time
    )

    fun toRating(): Rating {
        return Rating(
            value = value,
            review = review,
            user = User(userId),
            book = Book(bookId),
        )
    }

    override fun toString(): String {
        return "RatingDto(value=$value, review='$review', userId=$userId, bookId=$bookId)"
    }

}
