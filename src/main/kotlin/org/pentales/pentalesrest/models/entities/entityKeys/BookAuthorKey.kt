package org.pentales.pentalesrest.models.entities.entityKeys

import jakarta.persistence.*
import java.io.*

@Embeddable
class BookAuthorKey(
    var bookId: Long = 0L, var authorId: Long = 0L
) : Serializable {

    override fun toString(): String {
        return "BookAuthorKey(bookId=$bookId, authorId=$authorId)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as BookAuthorKey

        if (bookId != other.bookId) return false
        if (authorId != other.authorId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = bookId.hashCode()
        result = 31 * result + authorId.hashCode()
        return result
    }
}