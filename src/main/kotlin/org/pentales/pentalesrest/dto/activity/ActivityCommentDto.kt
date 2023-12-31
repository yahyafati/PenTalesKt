package org.pentales.pentalesrest.dto.activity

import org.pentales.pentalesrest.models.*

data class ActivityCommentDto(
    var comment: String = "",
) {

    constructor(comment: Comment) : this(
        comment = comment.comment,
    )
}
