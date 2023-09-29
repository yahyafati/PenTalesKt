package org.pentales.pentalesrest.models.intermediates

import jakarta.persistence.*
import org.pentales.pentalesrest.models.*
import java.io.*

@Entity
class BookPublisher(

    @EmbeddedId
    var id: BookPublisherKey? = null, var sortOrder: Int = 0,

    @MapsId("bookId")
    @ManyToOne
    var book: Book? = null,

    @MapsId("publisherId")
    @ManyToOne
    var publisher: Publisher? = null

)

@Embeddable
class BookPublisherKey(
    var bookId: Long?, var publisherId: Long?
) : Serializable