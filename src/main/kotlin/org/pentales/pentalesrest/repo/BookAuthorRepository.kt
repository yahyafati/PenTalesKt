package org.pentales.pentalesrest.repo

import org.pentales.pentalesrest.models.intermediates.*

interface BookAuthorRepository : IRepoSpecification<BookAuthor, Long> {

    fun deleteAllByBookId(bookId: Long)
}
