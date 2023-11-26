package org.pentales.pentalesrest.models.keys

import jakarta.persistence.*
import java.io.*

@Embeddable
class BookLanguageKey(
    var bookId: Long = 0L, var languageId: Long = 0L
) : Serializable {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as BookLanguageKey

        if (bookId != other.bookId) return false
        if (languageId != other.languageId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = bookId.hashCode()
        result = 31 * result + languageId.hashCode()
        return result
    }

    override fun toString(): String {
        return "BookLanguageKey(bookId=$bookId, languageId=$languageId)"
    }
}