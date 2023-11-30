package org.pentales.pentalesrest.dto

import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.models.keys.*

class AddRatingDto(
    var value: Int = 0, var review: String = ""
) {

    constructor(rating: Rating) : this(rating.value, rating.review)

    fun toRating(book: Book, user: User): Rating {
        val rating = Rating(
            book = book, user = user, value = value, review = review
        )
        rating.id = UserBookKey(bookId = book.id, userId = user.id)
        return rating
    }

    override fun toString(): String {
        return "AddRatingDto(value=$value, review='$review')"
    }

}