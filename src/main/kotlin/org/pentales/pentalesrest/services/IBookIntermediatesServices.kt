package org.pentales.pentalesrest.services

import org.pentales.pentalesrest.models.*

interface IBookIntermediatesServices {


    fun updateAuthors(bookId: Long, authors: List<Author>, delete: Boolean): Book
    fun removeAuthors(bookId: Long): Book
    fun updatePublishers(bookId: Long, publishers: List<Publisher>, delete: Boolean): Book
    fun removePublishers(bookId: Long): Book
    fun updateGenres(bookId: Long, genres: List<Genre>, delete: Boolean): Book
    fun removeGenres(bookId: Long): Book
    fun updateLanguages(bookId: Long, languages: List<Language>, delete: Boolean): Book
    fun removeLanguages(bookId: Long): Book
}