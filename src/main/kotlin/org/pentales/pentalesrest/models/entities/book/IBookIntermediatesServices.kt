package org.pentales.pentalesrest.models.entities.book

import org.pentales.pentalesrest.models.entities.book.author.*
import org.pentales.pentalesrest.models.entities.book.genre.*

interface IBookIntermediatesServices {

    fun updateAuthors(
        bookId: Long,
        authors: List<Author>,
        delete: Boolean
    ): Book

    fun removeAuthors(bookId: Long): Book
    fun updateGenres(
        bookId: Long,
        genres: List<Genre>,
        delete: Boolean
    ): Book

    fun removeGenres(bookId: Long): Book
}