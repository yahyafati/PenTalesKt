package org.pentales.pentalesrest.models.intermediates

import jakarta.persistence.*
import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.models.keys.*

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

