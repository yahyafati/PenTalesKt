package org.pentales.pentalesrest.models.entities.rating.comment

import org.pentales.pentalesrest.models.entities.rating.*
import org.pentales.pentalesrest.models.entities.user.*
import org.springframework.data.domain.*

interface ICommentServices {

    fun getCommentById(id: Long): Comment
    fun findAllByRating(rating: Rating, pageable: Pageable): Page<Comment>
    fun countAllByRating(rating: Rating): Long
    fun save(comment: Comment): Comment
    fun saveNew(comment: Comment): Comment
    fun deleteById(id: Long, user: User): Long
    fun updateComment(comment: Comment): Comment

}