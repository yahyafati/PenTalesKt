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

    override fun getBooksByStatus(
        userId: Long, status: EUserBookReadStatus, pageable: Pageable
    ): List<UserBookActivity> {
        return userBookActivityRepository.findAllByUserIdAndStatus(userId, status, pageable)
    }

    override fun getBooksCountByStatus(userId: Long, status: EUserBookReadStatus): Int {
        return userBookActivityRepository.countAllByUserIdAndStatus(userId, status)
    }

    override fun getBooksByStatusSince(
        userId: Long, status: EUserBookReadStatus, since: Long, pageable: Pageable
    ): List<UserBookActivity> {
        return userBookActivityRepository.findAllByUserIdAndStatusAndCreatedAtGreaterThanEqual(
            userId, status, Timestamp(since), pageable
        )
    }

    override fun getBooksCountByStatusSince(userId: Long, status: EUserBookReadStatus, since: Long): Int {
        return userBookActivityRepository.countAllByUserIdAndStatusAndCreatedAtGreaterThanEqual(
            userId, status, Timestamp(since)
        )
    }

}