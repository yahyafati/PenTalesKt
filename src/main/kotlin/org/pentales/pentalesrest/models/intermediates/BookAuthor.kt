package org.pentales.pentalesrest.models.intermediates

import jakarta.persistence.*
import org.pentales.pentalesrest.models.*
import java.io.*

@Entity
class BookAuthor(

    @EmbeddedId
    var id: BookAuthorKey? = null, var sortOrder: Int = 0,

    @MapsId("bookId")
    @ManyToOne
    var book: Book? = null,

    @MapsId("authorId")
    @ManyToOne
    var author: Author? = null

)

@Embeddable
class BookAuthorKey(
    var bookId: Long?, var authorId: Long?
) : Serializable