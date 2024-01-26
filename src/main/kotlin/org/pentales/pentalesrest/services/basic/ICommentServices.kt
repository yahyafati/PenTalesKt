package org.pentales.pentalesrest.services.basic

import org.pentales.pentalesrest.models.*
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