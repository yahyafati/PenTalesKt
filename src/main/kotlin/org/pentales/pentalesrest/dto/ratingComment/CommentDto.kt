package org.pentales.pentalesrest.dto.ratingComment

import org.pentales.pentalesrest.dto.user.*
import org.pentales.pentalesrest.models.*
import java.sql.*

data class CommentDto(
    var id: Long = 0,
    var comment: String = "",
    var user: UserDto = UserDto(),
    var createdAt: Long = Timestamp(System.currentTimeMillis()).time,
    var updatedAt: Long = Timestamp(System.currentTimeMillis()).time,
) {

    constructor(
        comment: Comment
    ) : this(
        id = comment.id,
        comment = comment.comment,
        user = UserDto(comment.user),
        createdAt = comment.createdAt.time,
        updatedAt = comment.updatedAt.time,
    )

    fun toComment(): Comment {
        val comment = Comment(
            comment = comment,
            user = user.toUser(),
        )
        comment.id = id
        comment.createdAt = Timestamp(createdAt)
        comment.updatedAt = Timestamp(updatedAt)
        return comment
    }

}