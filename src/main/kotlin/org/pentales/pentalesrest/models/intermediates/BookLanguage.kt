package org.pentales.pentalesrest.models.intermediates

import jakarta.persistence.*
import org.pentales.pentalesrest.models.*
import java.io.*

@Entity
class BookLanguage(

    @EmbeddedId var id: BookLanguageKey = BookLanguageKey(),
    var sortOrder: Int = 0,
    @MapsId("bookId") @ManyToOne var book: Book = Book(),
    @MapsId("languageId") @ManyToOne var language: Language = Language()

)

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