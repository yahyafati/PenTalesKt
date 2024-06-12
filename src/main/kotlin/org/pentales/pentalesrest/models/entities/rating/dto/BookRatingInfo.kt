package org.pentales.pentalesrest.models.entities.rating.dto

data class BookRatingInfo(
    var book: org.pentales.pentalesrest.models.entities.book.Book = org.pentales.pentalesrest.models.entities.book.Book(),
    var rating: Double = 0.0,
    var count: Long = 0,
    var reviewsCount: Long = 0,
    var distribution: List<RatingDistribution> = mutableListOf()
)
