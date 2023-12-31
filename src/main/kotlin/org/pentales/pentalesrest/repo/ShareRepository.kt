package org.pentales.pentalesrest.repo

import org.pentales.pentalesrest.models.*

interface ShareRepository : IRepoSpecification<Share, Long> {

    fun countAllByRating(rating: Rating): Int

}