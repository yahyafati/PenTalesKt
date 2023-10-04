package org.pentales.pentalesrest.models.intermediates

import jakarta.persistence.*
import org.pentales.pentalesrest.models.*
import java.io.*

@Entity
class BookPublisher(
    @EmbeddedId
    var id: BookPublisherKey = BookPublisherKey(),
    var sortOrder: Int = 0,
    @MapsId("bookId")
    @ManyToOne
    var book: Book = Book(),
    @MapsId("publisherId")
    @ManyToOne
    var publisher: Publisher = Publisher()
)

@Embeddable
class BookPublisherKey(
    var bookId: Long = 0L, var publisherId: Long = 0L
) : Serializable {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as BookPublisherKey

        if (bookId != other.bookId) return false
        if (publisherId != other.publisherId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = bookId.hashCode()
        result = 31 * result + publisherId.hashCode()
        return result
    }

    override fun toString(): String {
        return "BookPublisherKey(bookId=$bookId, publisherId=$publisherId)"
    }
}