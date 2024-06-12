package org.pentales.pentalesrest.models.entities.rating.comment.dtos

import org.pentales.pentalesrest.models.entities.rating.*
import org.pentales.pentalesrest.models.entities.rating.comment.*
import org.pentales.pentalesrest.models.entities.user.*

class AddCommentDto(
    var comment: String = "",
) {

    fun toComment(user: User, rating: Rating): Comment {
        return Comment(
            comment = comment,
            user = user,
            rating = rating,
        )
    }

    override fun toString(): String {
        return "AddRatingCommentDto(comment='$comment')"
    }

}