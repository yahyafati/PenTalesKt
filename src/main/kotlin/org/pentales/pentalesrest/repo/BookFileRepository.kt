package org.pentales.pentalesrest.repo

import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.repo.base.*

interface BookFileRepository : IRepoSpecification<BookFile, Long> {

    fun findByOwnerAndBook(owner: User, book: Book): BookFile?
    fun existsByOwnerAndBook(owner: User, book: Book): Boolean
}