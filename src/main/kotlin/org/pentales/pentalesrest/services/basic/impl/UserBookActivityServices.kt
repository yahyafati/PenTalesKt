package org.pentales.pentalesrest.services.basic.impl

import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.models.enums.*
import org.pentales.pentalesrest.repo.*
import org.pentales.pentalesrest.services.basic.*
import org.springframework.data.domain.*
import org.springframework.stereotype.*
import java.sql.*

@Service
class UserBookActivityServices(
    private val userBookActivityRepository: UserBookActivityRepository
) : IUserBookActivityServices {

    override fun getBookStatus(userId: Long, bookId: Long): UserBookReadStatus {
        val userBook = userBookActivityRepository.findLastByUserIdAndBookIdOrderByCreatedAt(userId, bookId)
        return userBook?.status ?: UserBookReadStatus.NONE
    }

    override fun getNowReadingBook(userId: Long): UserBookActivity? {
        return userBookActivityRepository.findLastByUserIdAndStatusOrderByCreatedAt(userId, UserBookReadStatus.READING)
    }

    override fun getBooksByStatus(
        userId: Long, status: UserBookReadStatus, pageable: Pageable
    ): List<UserBookActivity> {
        return userBookActivityRepository.findAllByUserIdAndStatus(userId, status, pageable)
    }

    override fun getBooksCountByStatus(userId: Long, status: UserBookReadStatus): Int {
        return userBookActivityRepository.countAllByUserIdAndStatus(userId, status)
    }

    override fun getBooksByStatusSince(
        userId: Long, status: UserBookReadStatus, since: Long, pageable: Pageable
    ): List<UserBookActivity> {
        return userBookActivityRepository.findAllByUserIdAndStatusAndCreatedAtGreaterThanEqual(
            userId, status, Timestamp(since), pageable
        )
    }

    override fun getBooksCountByStatusSince(userId: Long, status: UserBookReadStatus, since: Long): Int {
        return userBookActivityRepository.countAllByUserIdAndStatusAndCreatedAtGreaterThanEqual(
            userId, status, Timestamp(since)
        )
    }

}