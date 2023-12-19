package org.pentales.pentalesrest.dto.activity

import org.pentales.pentalesrest.dto.*
import org.pentales.pentalesrest.models.*

data class ActivityCommentDto(
    var comment: String = "",
) {

    constructor(ratingComment: RatingComment) : this(
        comment = ratingComment.comment,
    )
}
