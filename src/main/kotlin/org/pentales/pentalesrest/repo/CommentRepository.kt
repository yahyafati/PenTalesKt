package org.pentales.pentalesrest.repo

import org.pentales.pentalesrest.models.*
import org.springframework.data.domain.*

interface CommentRepository : IRepoSpecification<Comment, Long> {

    fun findAllByRating(rating: Rating, pageable: Pageable): Page<Comment>
    fun countAllByRating(rating: Rating): Long
}