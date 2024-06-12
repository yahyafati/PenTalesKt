package org.pentales.pentalesrest.models.entities.entityKeys

import jakarta.persistence.*
import java.io.*

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