package org.pentales.pentalesrest.dto.book

import com.fasterxml.jackson.annotation.*
import org.pentales.pentalesrest.dto.author.*
import org.pentales.pentalesrest.dto.rating.*
import org.pentales.pentalesrest.dto.user.*
import org.pentales.pentalesrest.models.*

data class ActivityBookDto(
    val id: Long = 0,
    val title: String = "",
    val coverImage: String = "",
    val authors: List<AuthorDto> = listOf(),
    val rating: BasicBookRatingDto = BasicBookRatingDto(),

    @JsonIgnore
    @Transient
    private val baseURL: String = "http://localhost:8080"
) {

    constructor(book: ActivityBook, baseURL: String) : this(
        id = book.id,
        title = book.title,
        coverImage = UserDto.getURLWithBaseURL(book.coverImage, baseURL) ?: "",
        authors = book.authors.map { AuthorDto(it.author) },
        rating = BasicBookRatingDto(
            average = book.__averageRating,
            count = book.__ratingCount,
        )
    )
}