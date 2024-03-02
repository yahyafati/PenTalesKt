package org.pentales.pentalesrest.models.keys

import jakarta.persistence.*
import java.io.*

@Embeddable
class UserBookFileKey(
    var userId: Long = 0,
    var bookFileId: Long = 0
) : Serializable {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is UserBookFileKey) return false

        if (userId != other.userId) return false
        if (bookFileId != other.bookFileId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = userId.hashCode()
        result = 31 * result + bookFileId.hashCode()
        return result
    }

    override fun toString(): String {
        return "UserBookFileKey(userId=$userId, bookId=$bookFileId)"
    }
}