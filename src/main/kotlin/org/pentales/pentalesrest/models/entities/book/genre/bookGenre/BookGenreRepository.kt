package org.pentales.pentalesrest.models.entities.book.genre.bookGenre

import org.pentales.pentalesrest.global.repo.base.*

interface BookGenreRepository : IRepoSpecification<BookGenre, Long> {

    fun deleteAllByBookId(bookId: Long)
}
