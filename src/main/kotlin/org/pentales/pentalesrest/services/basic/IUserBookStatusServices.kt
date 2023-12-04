package org.pentales.pentalesrest.services.basic

import org.pentales.pentalesrest.models.enums.*
import org.pentales.pentalesrest.models.intermediates.*
import org.springframework.data.domain.*

interface IUserBookStatusServices {

    fun getBookStatus(userId: Long, bookId: Long): UserBookStatus?
    fun getNowReadingBook(userId: Long): UserBookStatus?
    fun getBooksByStatus(userId: Long, status: EUserBookReadStatus, pageable: Pageable): List<UserBookStatus>
}