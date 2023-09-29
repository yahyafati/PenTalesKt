package org.pentales.pentalesrest.models.intermediates

import jakarta.persistence.*
import org.pentales.pentalesrest.models.*
import java.io.*

@Entity
class BookGenre(

    @EmbeddedId
    var bookGenreKey: BookGenreKey? = null, var sortOrder: Int = 0,

    @MapsId("bookId")
    @ManyToOne
    var book: Book? = null,

    @MapsId("genreId")
    @ManyToOne
    var genre: Genre? = null

)

@Embeddable
class BookGenreKey(
    var bookId: Long?, var genreId: Long?
) : Serializable