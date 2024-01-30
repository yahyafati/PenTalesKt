package org.pentales.pentalesrest.repo

import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.repo.base.*

interface ShareRepository : IRepoSpecification<Share, Long> {

    fun countAllByRating(rating: Rating): Int

    fun deleteAllByRating(rating: Rating)

}