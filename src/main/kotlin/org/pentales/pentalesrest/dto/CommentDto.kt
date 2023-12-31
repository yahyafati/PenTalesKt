package org.pentales.pentalesrest.dto

import org.pentales.pentalesrest.models.*

class CommentDto(
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