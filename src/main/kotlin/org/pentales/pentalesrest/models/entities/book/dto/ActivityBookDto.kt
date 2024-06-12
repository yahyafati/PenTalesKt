package org.pentales.pentalesrest.models.entities.book.dto

import com.fasterxml.jackson.annotation.*
import org.pentales.pentalesrest.models.entities.activity.book.*
import org.pentales.pentalesrest.models.entities.book.author.dto.*
import org.pentales.pentalesrest.models.entities.rating.dto.*
import org.pentales.pentalesrest.models.entities.user.dto.*

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