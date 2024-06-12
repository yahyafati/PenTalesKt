package org.pentales.pentalesrest.models.entities.book.userBook.activity

import org.pentales.pentalesrest.global.repo.base.*
import org.pentales.pentalesrest.models.entities.book.userBook.status.*
import org.springframework.data.domain.*
import java.sql.*

interface UserBookActivityRepository : IRepoSpecification<UserBookActivity, Long> {

    fun findAllByUserIdAndStatus(userId: Long, status: EUserBookReadStatus, pageable: Pageable): List<UserBookActivity>
    fun countAllByUserIdAndStatus(userId: Long, status: EUserBookReadStatus): Int
    fun findAllByUserIdAndStatusAndCreatedAtGreaterThanEqual(
        userId: Long, status: EUserBookReadStatus, createdAt: Timestamp, pageable: Pageable
    ): Page<UserBookActivity>

    fun findAllByUserIdAndStatusAndCreatedAtBetween(
        userId: Long, status: EUserBookReadStatus, startTime: Timestamp, endTime: Timestamp, pageable: Pageable
    ): Page<UserBookActivity>

    fun countAllByUserIdAndStatusAndCreatedAtGreaterThanEqual(
        userId: Long, status: EUserBookReadStatus, createdAt: Timestamp
    ): Int

    fun countAllByUserIdAndStatusAndCreatedAtBetween(
        userId: Long, status: EUserBookReadStatus, startTime: Timestamp, endTime: Timestamp
    ): Int

}