package org.pentales.pentalesrest.repo

import org.pentales.pentalesrest.models.*

interface ActivityRepository : IRepoSpecification<Activity, Long> {

    fun deleteByRating(rating: Rating): Long
    fun deleteByComment(comment: Comment): Long
    fun deleteByShare(share: Share): Long

}