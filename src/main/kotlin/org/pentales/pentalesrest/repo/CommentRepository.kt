package org.pentales.pentalesrest.repo

import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.repo.base.*
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