package org.pentales.pentalesrest.dto

import org.pentales.pentalesrest.models.*

data class RatingDto(
    val id: Long = 0,
    var value: Int = 0,
    var review: String = "",
    var user: UserDto = UserDto(),
    var book: BookDTO = BookDTO(),
    var createdAt: Long = 0,
    var updatedAt: Long = 0
) {

    constructor(rating: Rating) : this(
        id = rating.id,
        value = rating.value,
        review = rating.review,
        user = UserDto(rating.user),
        book = BookDTO(rating.book),
        createdAt = rating.createdAt.time,
        updatedAt = rating.updatedAt.time
    )

    fun toRating(): Rating {
        val rating = Rating(
            value = value,
            review = review,
            user = User(user.id),
            book = Book(book.id),
        )
        rating.id = id
        return rating
    }

}
