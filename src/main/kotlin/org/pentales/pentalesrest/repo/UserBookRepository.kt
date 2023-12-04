package org.pentales.pentalesrest.repo

import org.pentales.pentalesrest.models.enums.*
import org.pentales.pentalesrest.models.intermediates.*
import org.springframework.data.domain.*
import java.sql.*

interface UserBookRepository : IRepoSpecification<UserBook, Long> {

    fun findAllByUserIdAndStatus(userId: Long, status: UserBookReadStatus, pageable: Pageable): List<UserBook>
    fun countAllByUserIdAndStatus(userId: Long, status: UserBookReadStatus): Int
    fun findAllByUserIdAndStatusAndCreatedAtGreaterThanEqual(
        userId: Long, status: UserBookReadStatus, createdAt: Timestamp, pageable: Pageable
    ): List<UserBook>

    fun countAllByUserIdAndStatusAndCreatedAtGreaterThanEqual(
        userId: Long, status: UserBookReadStatus, createdAt: Timestamp
    ): Int

    fun findLastByUserIdAndBookIdOrderByCreatedAt(
        userId: Long, bookId: Long
    ): UserBook?

    fun findLastByUserIdAndStatusOrderByCreatedAt(
        userId: Long, status: UserBookReadStatus
    ): UserBook?
}