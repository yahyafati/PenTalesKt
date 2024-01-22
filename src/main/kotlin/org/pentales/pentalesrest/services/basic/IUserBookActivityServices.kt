package org.pentales.pentalesrest.services.basic

import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.models.enums.*
import org.springframework.data.domain.*

interface IUserBookActivityServices {

    fun addBookActivity(userBookActivity: UserBookActivity): UserBookActivity

    fun getBooksByStatus(userId: Long, status: EUserBookReadStatus, pageable: Pageable): List<UserBookActivity>
    fun getBooksCountByStatus(userId: Long, status: EUserBookReadStatus): Int
    fun getBooksByStatusSince(
        userId: Long, status: EUserBookReadStatus, since: Long, pageable: Pageable
    ): Page<UserBookActivity>

    fun getBooksByStatusInYear(
        userId: Long, status: EUserBookReadStatus, year: Int, pageable: Pageable
    ): Page<UserBookActivity>

    fun getBooksCountByStatusSince(userId: Long, status: EUserBookReadStatus, since: Long): Int

    fun getBooksCountByStatusInYear(userId: Long, status: EUserBookReadStatus, year: Int): Int
}