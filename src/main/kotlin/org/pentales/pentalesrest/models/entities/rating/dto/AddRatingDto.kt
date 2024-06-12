package org.pentales.pentalesrest.models.entities.rating.dto

import org.pentales.pentalesrest.models.entities.rating.*
import org.pentales.pentalesrest.models.entities.user.*

data class AddRatingDto(
    var value: Int = 0,
    var review: String = "",
) {

    constructor(rating: Rating) : this(rating.value, rating.review)

    fun toRating(book: org.pentales.pentalesrest.models.entities.book.Book, user: User): Rating {
        val rating = Rating(
            book = book, user = user, value = value, review = review
        )
        return rating
    }

    override fun toString(): String {
        return "AddRatingDto(value=$value, review='$review')"
    }

}