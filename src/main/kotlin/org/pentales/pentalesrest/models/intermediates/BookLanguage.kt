package org.pentales.pentalesrest.models.intermediates

import jakarta.persistence.*
import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.models.keys.*

@Entity
class BookLanguage(
    @EmbeddedId
    var id: BookLanguageKey = BookLanguageKey(),
    var sortOrder: Int = 0,
    @MapsId("bookId")
    @ManyToOne
    var book: Book = Book(),
    @MapsId("languageId")
    @ManyToOne
    var language: Language = Language()
)

