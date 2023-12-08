package org.pentales.pentalesrest.repo

import org.pentales.pentalesrest.models.*
import org.springframework.data.domain.*

interface RatingCommentRepository : IRepoSpecification<RatingComment, Long> {

    fun findAllByRating(rating: Rating, pageable: Pageable): Page<RatingComment>
    fun countAllByRating(rating: Rating): Long
}