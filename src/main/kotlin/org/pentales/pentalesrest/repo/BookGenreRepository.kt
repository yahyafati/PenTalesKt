package org.pentales.pentalesrest.repo

import org.pentales.pentalesrest.models.intermediates.*

interface BookGenreRepository : IRepoSpecification<BookGenre, Long> {

    fun deleteAllByBookId(bookId: Long)
}
