package org.pentales.pentalesrest.dto

import org.pentales.pentalesrest.models.*

class AddRatingCommentDto(
    var comment: String = "",
) {

    fun toRatingComment(user: User, rating: Rating): Comment {
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