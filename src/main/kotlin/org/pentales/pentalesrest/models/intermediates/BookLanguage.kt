package org.pentales.pentalesrest.models.intermediates

import jakarta.persistence.*
import org.pentales.pentalesrest.models.*
import java.io.*

@Entity
class BookLanguage(

    @EmbeddedId
    var id: BookLanguageKey = BookLanguageKey(),
    var sortOrder: Int = 0,
    @MapsId("bookId")
    @ManyToOne
    var book: Book? = null,
    @MapsId("languageId")
    @ManyToOne
    var language: Language? = null

)

@Embeddable
class BookLanguageKey(
    var bookId: Long = 0L, var languageId: Long = 0L
) : Serializable