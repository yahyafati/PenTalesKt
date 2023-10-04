package org.pentales.pentalesrest.models.intermediates

import jakarta.persistence.*
import org.pentales.pentalesrest.models.*
import java.io.*

@Entity
class BookAuthor(
    @EmbeddedId
    var id: BookAuthorKey = BookAuthorKey(),
    var sortOrder: Int = 0,
    @MapsId("bookId")
    @ManyToOne
    var book: Book = Book(),
    @MapsId("authorId")
    @ManyToOne
    var author: Author = Author()
)

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