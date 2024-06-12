package org.pentales.pentalesrest.models.entities.rating.comment

import org.pentales.pentalesrest.global.repo.base.*
import org.pentales.pentalesrest.models.entities.rating.*
import org.pentales.pentalesrest.models.entities.user.*
import org.springframework.data.domain.*
import org.springframework.data.jpa.repository.*

interface CommentRepository : IRepoSpecification<Comment, Long> {

    fun findAllByRating(rating: Rating, pageable: Pageable): Page<Comment>
    fun countAllByRating(rating: Rating): Long
    fun deleteCommentByIdAndUser(id: Long, user: User): Long

    @Query(
        "UPDATE Comment c SET c.hidden = true WHERE c.rating = :rating"
    )
    @Modifying
    fun hideByRating(rating: Rating): Int
}