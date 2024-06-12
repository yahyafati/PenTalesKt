package org.pentales.pentalesrest.models.entities.rating.share

import org.pentales.pentalesrest.global.repo.base.*
import org.pentales.pentalesrest.models.entities.rating.*

interface ShareRepository : IRepoSpecification<Share, Long> {

    fun countAllByRating(rating: Rating): Int

    fun deleteAllByRating(rating: Rating)

}