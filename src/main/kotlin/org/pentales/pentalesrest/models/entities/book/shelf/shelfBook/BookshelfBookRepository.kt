package org.pentales.pentalesrest.models.entities.book.shelf.shelfBook

import org.pentales.pentalesrest.global.repo.base.*
import org.pentales.pentalesrest.models.entities.activity.book.*
import org.pentales.pentalesrest.models.entities.entityKeys.*
import org.pentales.pentalesrest.models.entities.user.*

interface BookshelfBookRepository : IRepoSpecification<BookShelfBook, BookShelfBookKey> {

    fun deleteAllByBookShelfOwnerAndBook(owner: User, book: ActivityBook): Int
}