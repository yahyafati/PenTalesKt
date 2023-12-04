package org.pentales.pentalesrest.services.basic.impl

import org.pentales.pentalesrest.models.enums.*
import org.pentales.pentalesrest.models.intermediates.*
import org.pentales.pentalesrest.repo.*
import org.pentales.pentalesrest.services.basic.*
import org.springframework.data.domain.*
import org.springframework.stereotype.*
import java.sql.*

@Service
class UserBookServices(
    private val userBookRepository: UserBookRepository
) : IUserBookServices {

    override fun getBookStatus(userId: Long, bookId: Long): UserBookReadStatus {
        val userBook = userBookRepository.findLastByUserIdAndBookIdOrderByCreatedAt(userId, bookId)
        return userBook?.status ?: UserBookReadStatus.NONE
    }

    override fun getNowReadingBook(userId: Long): UserBook? {
        return userBookRepository.findLastByUserIdAndStatusOrderByCreatedAt(userId, UserBookReadStatus.READING)
    }

    override fun getBooksByStatus(userId: Long, status: UserBookReadStatus, pageable: Pageable): List<UserBook> {
        return userBookRepository.findAllByUserIdAndStatus(userId, status, pageable)
    }

    override fun getBooksCountByStatus(userId: Long, status: UserBookReadStatus): Int {
        return userBookRepository.countAllByUserIdAndStatus(userId, status)
    }

    override fun getBooksByStatusSince(
        userId: Long, status: UserBookReadStatus, since: Long, pageable: Pageable
    ): List<UserBook> {
        return userBookRepository.findAllByUserIdAndStatusAndCreatedAtGreaterThanEqual(
            userId, status, Timestamp(since), pageable
        )
    }

    override fun getBooksCountByStatusSince(userId: Long, status: UserBookReadStatus, since: Long): Int {
        return userBookRepository.countAllByUserIdAndStatusAndCreatedAtGreaterThanEqual(
            userId, status, Timestamp(since)
        )
    }

}