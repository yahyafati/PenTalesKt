package org.pentales.pentalesrest.dto.rating

data class BookRatingInfoDto(
    var rating: Double = 0.0,
    var count: Long = 0,
    var reviewsCount: Long = 0,
    var distribution: List<RatingDistribution> = mutableListOf()
) {

    constructor(rating: BookRatingInfo) : this(
        rating = rating.rating,
        count = rating.count,
        reviewsCount = rating.reviewsCount,
        distribution = rating.distribution
    )
}
