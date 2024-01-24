package org.pentales.pentalesrest.services.basic

import org.pentales.pentalesrest.dto.rating.*
import org.pentales.pentalesrest.models.*
import org.springframework.data.domain.*

interface IBookServices : IGenericService<Book> {

    fun getBookRatingInfo(bookId: Long, fetchBook: Boolean = false): BookRatingInfo
    fun getBookRatings(bookId: Long, pageable: Pageable): Page<Rating>
    fun getRelatedBooks(bookId: Long, pageable: Pageable): Page<Book>
    fun getBookRatingByUser(bookId: Long, userId: Long): Rating?
}
