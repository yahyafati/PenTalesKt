package org.pentales.pentalesrest.models.entities.entityKeys

import jakarta.persistence.*
import java.io.*

@Embeddable
class BookGenreKey(
    var bookId: Long = 0L, var genreId: Long = 0L
) : Serializable {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as BookGenreKey

        if (bookId != other.bookId) return false
        if (genreId != other.genreId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = bookId.hashCode()
        result = 31 * result + genreId.hashCode()
        return result
    }

    override fun toString(): String {
        return "BookGenreKey(bookId=$bookId, genreId=$genreId)"
    }
}