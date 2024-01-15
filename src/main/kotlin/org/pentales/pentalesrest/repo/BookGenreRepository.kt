package org.pentales.pentalesrest.repo

import org.pentales.pentalesrest.models.intermediates.*
import org.pentales.pentalesrest.repo.base.IRepoSpecification

interface BookGenreRepository : IRepoSpecification<BookGenre, Long> {

    fun deleteAllByBookId(bookId: Long)
}
