package org.pentales.pentalesrest.models.entities.book.userBook.status

import org.springframework.data.domain.*

interface IUserBookStatusServices {

    fun updateBookStatus(userId: Long, bookId: Long, status: EUserBookReadStatus): UserBookStatus
    fun getBookStatus(userId: Long, bookId: Long): EUserBookReadStatus
    fun getNowReadingBook(userId: Long): UserBookStatus?
    fun getNowReadingBook(username: String): UserBookStatus?
    fun getBooksByStatus(userId: Long, status: EUserBookReadStatus, pageable: Pageable): List<UserBookStatus>
}