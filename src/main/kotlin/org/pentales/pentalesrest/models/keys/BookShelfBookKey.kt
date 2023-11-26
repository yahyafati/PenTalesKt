package org.pentales.pentalesrest.models.keys

import jakarta.persistence.*
import java.io.*

@Embeddable
class BookShelfBookKey(
    var bookShelfId: Long = 0L, var bookId: Long = 0L
) : Serializable {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as BookShelfBookKey

        if (bookShelfId != other.bookShelfId) return false
        if (bookId != other.bookId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = bookShelfId.hashCode()
        result = 31 * result + bookId.hashCode()
        return result
    }

    override fun toString(): String {
        return "BookShelfBookKey(bookShelfId=$bookShelfId, bookId=$bookId)"
    }
}
