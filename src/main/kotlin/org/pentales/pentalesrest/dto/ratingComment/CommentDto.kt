package org.pentales.pentalesrest.dto.ratingComment

import org.pentales.pentalesrest.dto.user.*
import org.pentales.pentalesrest.models.*

data class CommentDto(
    var id: Long = 0,
    var comment: String = "",
    var user: UserDto = UserDto(),
) {

    constructor(
        comment: Comment
    ) : this(
        id = comment.id,
        comment = comment.comment,
        user = UserDto(comment.user),
    )

    override fun toString(): String {
        return "RatingCommentDto(id=$id, comment='$comment', user=$user)"
    }

}