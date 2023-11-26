package org.pentales.pentalesrest.models.intermediates

import jakarta.persistence.*
import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.models.keys.*

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

