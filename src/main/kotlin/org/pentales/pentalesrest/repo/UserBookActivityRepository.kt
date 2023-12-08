package org.pentales.pentalesrest.repo

import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.models.enums.*
import org.springframework.data.domain.*
import java.sql.*

interface UserBookActivityRepository : IRepoSpecification<UserBookActivity, Long> {

    fun findAllByUserIdAndStatus(userId: Long, status: EUserBookReadStatus, pageable: Pageable): List<UserBookActivity>
    fun countAllByUserIdAndStatus(userId: Long, status: EUserBookReadStatus): Int
    fun findAllByUserIdAndStatusAndCreatedAtGreaterThanEqual(
        userId: Long, status: EUserBookReadStatus, createdAt: Timestamp, pageable: Pageable
    ): List<UserBookActivity>

    fun countAllByUserIdAndStatusAndCreatedAtGreaterThanEqual(
        userId: Long, status: EUserBookReadStatus, createdAt: Timestamp
    ): Int

}