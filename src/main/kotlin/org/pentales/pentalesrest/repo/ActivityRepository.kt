package org.pentales.pentalesrest.repo

import org.pentales.pentalesrest.models.*

interface ActivityRepository : IRepoSpecification<Activity, Long> {

    fun deleteByRating(rating: Rating): Long
    fun deleteByRatingComment(comment: RatingComment): Long
    fun deleteByShare(share: ActivityShare): Long

}