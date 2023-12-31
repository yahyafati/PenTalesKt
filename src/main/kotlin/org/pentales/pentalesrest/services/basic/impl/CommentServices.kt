package org.pentales.pentalesrest.services.basic.impl

import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.repo.*
import org.pentales.pentalesrest.services.basic.*
import org.springframework.data.domain.*
import org.springframework.stereotype.*

@Service
class CommentServices(
    private val repository: CommentRepository,
) : ICommentServices {

    override fun findAllByRating(rating: Rating, pageable: Pageable): Page<Comment> {
        return repository.findAllByRating(rating, pageable)
    }

    override fun countAllByRating(rating: Rating): Long {
        return repository.countAllByRating(rating)
    }

    override fun save(comment: Comment): Comment {
        return repository.save(comment)
    }

    override fun saveNew(comment: Comment): Comment {
        comment.id = 0
        return save(comment)
    }

    override fun deleteById(id: Long) {
        repository.deleteById(id)
    }

}