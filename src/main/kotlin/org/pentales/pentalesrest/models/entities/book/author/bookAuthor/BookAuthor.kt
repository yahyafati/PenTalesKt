package org.pentales.pentalesrest.models.entities.book.author.bookAuthor

import jakarta.persistence.*
import org.pentales.pentalesrest.models.entities.book.author.*
import org.pentales.pentalesrest.models.entities.entityKeys.*

@Entity
class BookAuthor(
    @EmbeddedId
    var id: BookAuthorKey = BookAuthorKey(),
    var sortOrder: Int = 0,
    @MapsId("bookId")
    @ManyToOne
    var book: org.pentales.pentalesrest.models.entities.book.Book = org.pentales.pentalesrest.models.entities.book.Book(),
    @MapsId("authorId")
    @ManyToOne
    var author: Author = Author()
)

