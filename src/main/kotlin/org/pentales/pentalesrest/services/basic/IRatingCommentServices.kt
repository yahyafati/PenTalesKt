package org.pentales.pentalesrest.services.basic

import org.pentales.pentalesrest.models.*
import org.springframework.data.domain.*

interface IRatingCommentServices {

    fun findAllByRating(rating: Rating, pageable: Pageable): Page<RatingComment>
    fun countAllByRating(rating: Rating): Long
    fun save(ratingComment: RatingComment): RatingComment
    fun saveNew(ratingComment: RatingComment): RatingComment
    fun deleteById(id: Long)

}