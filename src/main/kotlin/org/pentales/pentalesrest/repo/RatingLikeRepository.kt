package org.pentales.pentalesrest.repo

import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.models.keys.*
import org.pentales.pentalesrest.repo.base.*

interface RatingLikeRepository : IRepoSpecification<RatingLike, UserRatingKey> {

    fun countAllByRating(rating: Rating): Long
    fun existsByRatingAndUser(rating: Rating, user: User): Boolean
}