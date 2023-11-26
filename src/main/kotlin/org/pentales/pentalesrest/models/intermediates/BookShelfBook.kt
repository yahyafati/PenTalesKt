package org.pentales.pentalesrest.models.intermediates

import jakarta.persistence.*
import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.models.keys.*

@Entity
class BookShelfBook(
    @EmbeddedId
    var id: BookShelfBookKey = BookShelfBookKey(), var sortOrder: Int = 0,

    @MapsId("bookId")
    @ManyToOne
    var book: Book = Book(),

    @MapsId("bookShelfId")
    @ManyToOne
    var bookShelf: BookShelf = BookShelf()
) {}