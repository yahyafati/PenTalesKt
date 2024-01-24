package org.pentales.pentalesrest.dto.rating

import org.pentales.pentalesrest.models.*

data class BookRatingInfo(
    var book: Book = Book(),
    var rating: Double = 0.0,
    var count: Long = 0,
    var reviewsCount: Long = 0,
    var distribution: List<RatingDistribution> = mutableListOf()
)
