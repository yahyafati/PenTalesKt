package org.pentales.pentalesrest.models.intermediates

import jakarta.persistence.*
import org.pentales.pentalesrest.models.*
import java.io.*

@Entity
class BookLanguage(

    @EmbeddedId
    var id: BookLanguageKey? = null, var sortOrder: Int = 0,

    @MapsId("bookId")
    @ManyToOne
    var book: Book? = null,

    @MapsId("languageId")
    @ManyToOne
    var language: Language? = null

)

@Embeddable
class BookLanguageKey(
    var bookId: Long?, var languageId: Long?
) : Serializable