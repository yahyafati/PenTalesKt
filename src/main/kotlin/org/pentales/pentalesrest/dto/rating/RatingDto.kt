package org.pentales.pentalesrest.dto.rating

import org.pentales.pentalesrest.dto.book.*
import org.pentales.pentalesrest.dto.user.*
import org.pentales.pentalesrest.models.*

data class RatingDto(
    val id: Long = 0,
    var value: Int = 0,
    var review: String = "",
    var user: UserDto = UserDto(),
    var book: BookDTO = BookDTO(),
    var createdAt: Long = 0,
    var updatedAt: Long = 0,
    var likes: Long = 0,
    var isLiked: Boolean = false,
) {

    constructor(rating: Rating, baseURL: String) : this(
        id = rating.id,
        value = rating.value,
        review = rating.review,
        user = UserDto(rating.user, baseURL),
        book = BookDTO(rating.book),
        createdAt = rating.createdAt.time,
        updatedAt = rating.updatedAt.time,
        likes = rating.__likes,
        isLiked = rating.__isLiked,
    )

    fun toRating(): Rating {
        val rating = Rating(
            id = id,
            value = value,
            review = review,
            user = User(user.id),
            book = Book(book.id),
        )
        rating.__likes = likes
        rating.__isLiked = isLiked

        return rating
    }

}
