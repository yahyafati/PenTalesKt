package org.pentales.pentalesrest.dto.book

import org.pentales.pentalesrest.dto.author.*
import org.pentales.pentalesrest.dto.rating.*
import org.pentales.pentalesrest.models.*

data class ActivityBookDto(
    val id: Long = 0,
    val title: String = "",
    val coverImage: String = "",
    val authors: List<AuthorDto> = listOf(),
    val rating: BasicBookRatingDto = BasicBookRatingDto(),
) {

    constructor(book: ActivityBook) : this(
        id = book.id,
        title = book.title,
        coverImage = book.coverImage,
        authors = book.authors.map { AuthorDto(it.author) },
        rating = BasicBookRatingDto(
            average = book.__averageRating,
            count = book.__ratingCount,
        )
    )
}