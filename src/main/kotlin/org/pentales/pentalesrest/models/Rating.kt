package org.pentales.pentalesrest.models

import jakarta.persistence.*
import jakarta.validation.constraints.*
import org.pentales.pentalesrest.models.interfaces.*
import org.pentales.pentalesrest.models.keys.*

@Entity
class Rating(
    @EmbeddedId
    var id: UserBookKey = UserBookKey(),

    @MapsId("userId")
    @ManyToOne
    var user: User? = null,
    @MapsId("bookId")
    @ManyToOne
    var book: Book? = null,

    @field:Min(1)
    @field:Max(5)
    var value: Int = 3,
    var review: String = "",
) : IAudit()