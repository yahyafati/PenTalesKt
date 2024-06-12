package org.pentales.pentalesrest.models.entities.rating.like

import org.pentales.pentalesrest.global.repo.base.*
import org.pentales.pentalesrest.models.entities.entityKeys.*
import org.pentales.pentalesrest.models.entities.rating.*
import org.pentales.pentalesrest.models.entities.user.*

interface RatingLikeRepository : IRepoSpecification<RatingLike, UserRatingKey> {

    fun countAllByRating(rating: Rating): Long
    fun existsByRatingAndUser(rating: Rating, user: User): Boolean
}