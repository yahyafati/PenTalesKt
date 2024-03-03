package org.pentales.pentalesrest.services

import org.pentales.pentalesrest.models.*
import org.springframework.data.domain.*

interface IBookShelfServices : IGenericService<BookShelf> {

    fun findAllByOwner(owner: User, pageable: Pageable): Page<BookShelf>
    fun findAllByOwnerUsername(username: String, pageable: Pageable): Page<BookShelf>
    fun findReadLater(owner: User): BookShelf
    fun findReadLater(username: String): BookShelf
    fun removeBookFromAllShelves(user: User, book: Book): Int
    fun addBookToShelves(user: User, book: Book, shelves: List<BookShelf>, removeExisting: Boolean): Int
}