package org.pentales.pentalesrest.models.entities.book.shelf

import org.pentales.pentalesrest.global.services.*
import org.pentales.pentalesrest.models.entities.user.*
import org.springframework.data.domain.*

interface IBookShelfServices : IGenericService<BookShelf> {

    fun findAllByOwner(owner: User, pageable: Pageable): Page<BookShelf>
    fun findAllByOwnerUsername(username: String, pageable: Pageable): Page<BookShelf>
    fun findReadLater(owner: User): BookShelf
    fun findReadLater(username: String): BookShelf
    fun removeBookFromAllShelves(user: User, book: org.pentales.pentalesrest.models.entities.book.Book): Int
    fun addBookToShelves(
        user: User,
        book: org.pentales.pentalesrest.models.entities.book.Book,
        shelves: List<BookShelf>,
        removeExisting: Boolean
    ): Int
}