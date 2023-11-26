package org.pentales.pentalesrest.models.keys

import jakarta.persistence.*
import java.io.*

@Embeddable
class UserBookKey(
    var userId: Long = 0, var bookId: Long = 0
) : Serializable {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is UserBookKey) return false

        if (userId != other.userId) return false
        if (bookId != other.bookId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = userId.hashCode()
        result = 31 * result + bookId.hashCode()
        return result
    }

    override fun toString(): String {
        return "UserBookKey(userId=$userId, bookId=$bookId)"
    }
}