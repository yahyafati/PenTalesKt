package org.pentales.pentalesrest.dto.activity

import org.pentales.pentalesrest.dto.*
import org.pentales.pentalesrest.models.*

data class ActivityCommentDto(
    var comment: String = "",
    var bookId: Long = 0,
    var user: UserDto = UserDto(),
) {

    constructor(ratingComment: RatingComment) : this(
        comment = ratingComment.comment,
        bookId = ratingComment.rating.id.bookId,
        user = UserDto(ratingComment.user),
    )
}
