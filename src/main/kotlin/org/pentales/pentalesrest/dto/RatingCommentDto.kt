package org.pentales.pentalesrest.dto

import org.pentales.pentalesrest.models.*

class RatingCommentDto(
    var id: Long = 0,
    var comment: String = "",
    var user: UserDto = UserDto(),
) {

    constructor(
        ratingComment: RatingComment
    ) : this(
        id = ratingComment.id,
        comment = ratingComment.comment,
        user = UserDto(ratingComment.user),
    )

    override fun toString(): String {
        return "RatingCommentDto(id=$id, comment='$comment', user=$user)"
    }

}