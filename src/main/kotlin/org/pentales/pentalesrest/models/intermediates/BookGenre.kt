package org.pentales.pentalesrest.models.intermediates

import jakarta.persistence.*
import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.models.keys.*

@Entity
class BookGenre(
    @EmbeddedId
    var id: BookGenreKey = BookGenreKey(),
    var sortOrder: Int = 0,
    @MapsId("bookId")
    @ManyToOne
    var book: Book = Book(),
    @MapsId("genreId")
    @ManyToOne
    var genre: Genre = Genre()
)

