package org.pentales.pentalesrest.models.entities.book.author.bookAuthor

import org.pentales.pentalesrest.global.repo.base.*

interface BookAuthorRepository : IRepoSpecification<BookAuthor, Long> {

    fun deleteAllByBookId(bookId: Long)
}
