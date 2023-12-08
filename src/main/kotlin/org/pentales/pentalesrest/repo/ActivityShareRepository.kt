package org.pentales.pentalesrest.repo

import org.pentales.pentalesrest.models.*

interface ActivityShareRepository : IRepoSpecification<ActivityShare, Long> {

    fun countAllByRating(rating: Rating): Int

}