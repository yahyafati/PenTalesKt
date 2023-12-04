package org.pentales.pentalesrest.services.basic

import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.models.enums.*
import org.springframework.data.domain.*

interface IUserBookActivityServices {

    fun getBookStatus(userId: Long, bookId: Long): UserBookReadStatus
    fun getNowReadingBook(userId: Long): UserBookActivity?
    fun getBooksByStatus(userId: Long, status: UserBookReadStatus, pageable: Pageable): List<UserBookActivity>
    fun getBooksCountByStatus(userId: Long, status: UserBookReadStatus): Int
    fun getBooksByStatusSince(
        userId: Long, status: UserBookReadStatus, since: Long, pageable: Pageable
    ): List<UserBookActivity>

    fun getBooksCountByStatusSince(userId: Long, status: UserBookReadStatus, since: Long): Int
}