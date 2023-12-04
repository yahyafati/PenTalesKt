package org.pentales.pentalesrest.repo

import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.models.enums.*
import org.springframework.data.domain.*
import java.sql.*

interface UserBookActivityRepository : IRepoSpecification<UserBookActivity, Long> {

    fun findAllByUserIdAndStatus(userId: Long, status: UserBookReadStatus, pageable: Pageable): List<UserBookActivity>
    fun countAllByUserIdAndStatus(userId: Long, status: UserBookReadStatus): Int
    fun findAllByUserIdAndStatusAndCreatedAtGreaterThanEqual(
        userId: Long, status: UserBookReadStatus, createdAt: Timestamp, pageable: Pageable
    ): List<UserBookActivity>

    fun countAllByUserIdAndStatusAndCreatedAtGreaterThanEqual(
        userId: Long, status: UserBookReadStatus, createdAt: Timestamp
    ): Int

    fun findLastByUserIdAndBookIdOrderByCreatedAt(
        userId: Long, bookId: Long
    ): UserBookActivity?

    fun findLastByUserIdAndStatusOrderByCreatedAt(
        userId: Long, status: UserBookReadStatus
    ): UserBookActivity?
}