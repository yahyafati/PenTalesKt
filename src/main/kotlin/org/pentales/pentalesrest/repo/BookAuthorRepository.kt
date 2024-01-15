package org.pentales.pentalesrest.repo

import org.pentales.pentalesrest.models.intermediates.*
import org.pentales.pentalesrest.repo.base.IRepoSpecification

interface BookAuthorRepository : IRepoSpecification<BookAuthor, Long> {

    fun deleteAllByBookId(bookId: Long)
}
