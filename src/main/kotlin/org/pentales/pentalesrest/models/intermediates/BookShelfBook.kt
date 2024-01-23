package org.pentales.pentalesrest.models.intermediates

import jakarta.persistence.*
import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.models.keys.*

@Entity
class BookShelfBook(
    @EmbeddedId
    var id: BookShelfBookKey = BookShelfBookKey(),

    @MapsId("bookId")
    @ManyToOne
    var book: Book = Book(),

    @MapsId("bookShelfId")
    @ManyToOne
    var bookShelf: BookShelf = BookShelf(),

    var sortOrder: Int = 0,
)