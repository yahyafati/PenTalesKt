package org.pentales.pentalesrest.repo

import org.pentales.pentalesrest.models.intermediates.*
import org.springframework.data.jpa.repository.*

interface BookLanguageRepository : JpaRepository<BookLanguage, Long> {

    fun deleteAllByBookId(bookId: Long)
}