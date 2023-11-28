package org.pentales.pentalesrest.services.basic

import org.pentales.pentalesrest.models.*

interface IBookIntermediatesServices {

    fun updateAuthors(bookId: Long, authors: List<Author>, delete: Boolean): Book
    fun removeAuthors(bookId: Long): Book
    fun updateGenres(bookId: Long, genres: List<Genre>, delete: Boolean): Book
    fun removeGenres(bookId: Long): Book
}