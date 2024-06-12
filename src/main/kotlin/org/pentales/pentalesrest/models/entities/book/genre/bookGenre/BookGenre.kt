package org.pentales.pentalesrest.models.entities.book.genre.bookGenre

import jakarta.persistence.*
import org.pentales.pentalesrest.models.entities.book.genre.*
import org.pentales.pentalesrest.models.entities.entityKeys.*

@Entity
class BookGenre(
    @EmbeddedId
    var id: BookGenreKey = BookGenreKey(),
    var sortOrder: Int = 0,
    @MapsId("bookId")
    @ManyToOne
    var book: org.pentales.pentalesrest.models.entities.book.Book = org.pentales.pentalesrest.models.entities.book.Book(),
    @MapsId("genreId")
    @ManyToOne
    var genre: Genre = Genre()
)

