package org.pentales.pentalesrest.models.entities.rating.comment.dtos

import org.pentales.pentalesrest.models.entities.rating.comment.*
import org.pentales.pentalesrest.models.entities.user.dto.*
import java.sql.*

data class CommentDto(
    var id: Long = 0,
    var comment: String = "",
    var user: UserDto = UserDto(),
    var createdAt: Long = Timestamp(System.currentTimeMillis()).time,
    var updatedAt: Long = Timestamp(System.currentTimeMillis()).time,
) {

    constructor(
        comment: Comment, baseURL: String
    ) : this(
        id = comment.id,
        comment = comment.comment,
        user = UserDto(comment.user, baseURL),
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