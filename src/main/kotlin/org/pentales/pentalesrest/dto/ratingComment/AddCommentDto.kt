package org.pentales.pentalesrest.dto.ratingComment

import org.pentales.pentalesrest.models.*

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