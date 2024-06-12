package org.pentales.pentalesrest.models.entities.book.file

import org.pentales.pentalesrest.global.repo.base.*
import org.pentales.pentalesrest.models.entities.user.*

interface BookFileRepository : IRepoSpecification<BookFile, Long> {

    fun findByOwnerAndBook(owner: User, book: org.pentales.pentalesrest.models.entities.book.Book): BookFile?
    fun existsByOwnerAndBook(owner: User, book: org.pentales.pentalesrest.models.entities.book.Book): Boolean
}