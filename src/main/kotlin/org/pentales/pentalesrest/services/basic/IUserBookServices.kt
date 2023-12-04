package org.pentales.pentalesrest.services.basic

import org.pentales.pentalesrest.models.enums.*
import org.pentales.pentalesrest.models.intermediates.*
import org.springframework.data.domain.*

interface IUserBookServices {

    fun getBookStatus(userId: Long, bookId: Long): UserBookReadStatus
    fun getNowReadingBook(userId: Long): UserBook?
    fun getBooksByStatus(userId: Long, status: UserBookReadStatus, pageable: Pageable): List<UserBook>
    fun getBooksCountByStatus(userId: Long, status: UserBookReadStatus): Int
    fun getBooksByStatusSince(userId: Long, status: UserBookReadStatus, since: Long, pageable: Pageable): List<UserBook>
    fun getBooksCountByStatusSince(userId: Long, status: UserBookReadStatus, since: Long): Int
}