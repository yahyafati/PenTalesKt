package org.pentales.pentalesrest.repo

import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.models.intermediates.*
import org.pentales.pentalesrest.models.keys.*
import org.pentales.pentalesrest.repo.base.*

interface BookshelfBookRepository : IRepoSpecification<BookShelfBook, BookShelfBookKey> {

    fun deleteAllByBookShelfOwnerAndBook(owner: User, book: ActivityBook): Int
}